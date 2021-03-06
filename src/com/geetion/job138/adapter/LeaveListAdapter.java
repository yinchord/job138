package com.geetion.job138.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.liqi.job.R;
import com.geetion.job138.activity.BaseActivity;
import com.geetion.job138.animation.ExpandAnimation;
import com.geetion.job138.model.Comment;
import com.geetion.job138.service.JobManageService;
import com.geetion.job138.service.PersonInfoSave;
import com.geetion.job138.util.AndroidUtil;
import com.geetion.job138.util.MyHttpException;
import com.geetion.job138.util.UIUtil;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LeaveListAdapter extends ArrayAdapter<Comment> {
	private LayoutInflater mInflater;
	private int mResource = R.layout.item_leave;
	private Activity context;
	private Map<Integer, Boolean> showReplyMap = new HashMap<Integer, Boolean>();
	private boolean isDeleting = false;
	private boolean isSending = false;
	private Handler handler = new Handler();
	private List<Comment> list;
	private Thread delThread, sendThread;

	public LeaveListAdapter(Activity context, List<Comment> list) {
		super(context, 0, list);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.list = list;
	}

	public void sentLeave(final String content, final Comment comment, final ViewHolder holder) {
		BaseActivity activity = (BaseActivity) context;
		activity.showLoadiing();
		isSending = true;
		sendThread = new Thread(new Runnable() {
			int result = -1;

			public void run() {
				try {
					result = JobManageService.replyComments(PersonInfoSave.memberInfo.getUserName(), content, comment.getId());
				} catch (MyHttpException e) {
					UIUtil.toast(context, e.getErrorMessage());
					result = -1;
					isSending = false;
				}
				context.runOnUiThread(new Runnable() {
					public void run() {
						if (result != -1) {
							comment.setReply(1);
							holder.getReplyButton().performClick();
							UIUtil.toast(context, "回复成功");
							notifyDataSetChanged();
						}
						isSending = false;
						BaseActivity activity = (BaseActivity) context;
						activity.hideLoading();
					}
				});
			}
		});
		sendThread.start();
	}

	public void deleLeave(final Comment comment, final int position) {
		BaseActivity activity = (BaseActivity) context;
		activity.showLoadiing();
		isDeleting = true;
		delThread = new Thread(new Runnable() {
			int result = -1;

			public void run() {
				try {
					result = JobManageService.delComments(comment.getId());
				} catch (MyHttpException e) {
					UIUtil.toast(context, e.getErrorMessage());
					result = -1;
					isDeleting = false;
				}
				context.runOnUiThread(new Runnable() {
					public void run() {
						if (result != -1) {
							UIUtil.toast(context, "删除成功");
							list.remove(position);
						}
						notifyDataSetChanged();
						isDeleting = false;
						BaseActivity activity = (BaseActivity) context;
						activity.hideLoading();
					}
				});
			}
		});
		delThread.start();
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		View layout = convertView;
		final ViewHolder holder;
		if (layout == null) {
			layout = mInflater.inflate(mResource, parent, false);
			holder = new ViewHolder(layout);
			layout.setTag(holder);
		} else {
			holder = (ViewHolder) layout.getTag();
		}
		final View replyLayout = holder.getReplyLayout();
		if (showReplyMap.containsKey(position)) {
			((LinearLayout.LayoutParams) replyLayout.getLayoutParams()).bottomMargin = 0;
			replyLayout.setVisibility(View.VISIBLE);
		} else {
			((LinearLayout.LayoutParams) replyLayout.getLayoutParams()).bottomMargin = -AndroidUtil.dpToPx(88, context);
			replyLayout.setVisibility(View.GONE);
		}
		final Comment comment = getItem(position);
		holder.getName().setText(comment.getName());
		holder.getData().setText(comment.getTime());
		holder.getContent().setText(comment.getContent());
		ImageButton replyButton = holder.getReplyButton();
		if (comment.getReply() == 0) {
			replyButton.setImageResource(R.drawable.reply_btn);
			replyButton.setClickable(true);
			replyButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					ExpandAnimation expandAni = new ExpandAnimation(replyLayout, 200);
					replyLayout.startAnimation(expandAni);
					if (showReplyMap.containsKey(position)) {
						showReplyMap.remove(position);
					} else {
						showReplyMap.put(position, true);
					}
				}
			});
		} else {
			replyButton.setImageResource(R.drawable.btn_reply_finished);
			replyButton.setClickable(false);
		}
		holder.getDelButton().setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (isDeleting) {
					UIUtil.toast(context, "请稍后...");
					return;
				}
				UIUtil.confirm(context, "提示", "是否要删除?", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						deleLeave(comment, position);
					}
				});
			}
		});
		holder.getSendButton().setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (isSending) {
					UIUtil.toast(context, "请稍后...");
					return;
				}
				final String content = holder.getContent().getText().toString();
				if (TextUtils.isEmpty(content)) {
					UIUtil.toast(context, "内容不能为空");
					return;
				}
				sentLeave(content, comment, holder);
			}
		});
		return layout;
	}

	public class ViewHolder {
		private View replyLayout;
		private ImageButton replyButton;
		private ImageButton delButton;
		private ImageButton sendButton;
		private EditText replyContent;
		private TextView date;
		private TextView name;
		private TextView content;
		private View baseView;

		public ViewHolder(View baseView) {
			this.baseView = baseView;
		}

		public View getReplyLayout() {
			if (replyLayout == null)
				replyLayout = baseView.findViewById(R.id.reply_layout);
			return replyLayout;
		}

		public ImageButton getReplyButton() {
			if (replyButton == null)
				replyButton = (ImageButton) baseView.findViewById(R.id.reply);
			return replyButton;
		}

		public ImageButton getDelButton() {
			if (delButton == null)
				delButton = (ImageButton) baseView.findViewById(R.id.del);
			return delButton;
		}

		public ImageButton getSendButton() {
			if (sendButton == null)
				sendButton = (ImageButton) baseView.findViewById(R.id.button_send);
			return sendButton;
		}

		public EditText getReplyContent() {
			if (replyContent == null)
				replyContent = (EditText) baseView.findViewById(R.id.reply_content);
			return replyContent;
		}

		public TextView getData() {
			if (date == null)
				date = (TextView) baseView.findViewById(R.id.date);
			return date;
		}

		public TextView getName() {
			if (name == null)
				name = (TextView) baseView.findViewById(R.id.company_name);
			return name;
		}

		public TextView getContent() {
			if (content == null)
				content = (TextView) baseView.findViewById(R.id.textView1);
			return content;
		}

	}
}
