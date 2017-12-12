package com.market.view;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haoyikuai.shop.android.R;
import com.market.utils.Constants;
import com.market.utils.Log;

public class MessageDialog extends Dialog {
	private ArrayList<String> tags;
	private ArrayList<RelativeLayout> relButtons;
	private ArrayList<android.view.View.OnClickListener> lterButtons;
	private ArrayList<String> descButtons;

	private Context context;
	private Resources resources;

	private LinearLayout ll_buttonList;
	private RelativeLayout rel_messagecontent;
	private TextView tv_messagetitle;
	private RelativeLayout.LayoutParams rellp;
	private android.view.View.OnClickListener listener;

	private DIALOGTYPE dl = DIALOGTYPE.defualt;
	private String dialogtitle;
	private String[] contents;
	private int winWidth;
	private int winHeight;

	private final int strConfirm = R.string.messageDialog_confirm;
	private final int strCancel = R.string.messageDialog_cancel;
	private final int colorContentText = R.color.common_texthightlight_color;
	private final int colorTitleText = R.color.common_texthightlight_color;

	public MessageDialog(Context context, int theme) {
		super(context, theme);
		this.init(context);
	}

	public MessageDialog(Context context) {
		super(context, R.style.FullHeightDialog);
		this.init(context);
	}

	public MessageDialog(Context context, ArrayList<String> descButtons) {
		super(context, R.style.FullHeightDialog);
		this.setDialogType(DIALOGTYPE.users);
		this.setDescButtons(descButtons);
		this.init(context);
	}

	protected MessageDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.init(context);
	}

	private void init(Context context) {
		this.context = context;
		resources = context.getResources();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.view_messagedialog);
		initWindow();
		createDialog();
		setTitle();
		setContent();

	}

	private void initWindow() {
		ll_buttonList = (LinearLayout) this
				.findViewById(R.id.ll_messagebuttons);
		Display display = this.getWindow().getWindowManager()
				.getDefaultDisplay();
		WindowManager.LayoutParams lp = this.getWindow().getAttributes();
		winWidth = lp.width = (int) (display.getWidth() * 0.8); // 占屏80％
		this.getWindow().setAttributes(lp);
		this.getWindow().setGravity(Gravity.CENTER); // 　默认为中间位置
	}

	/**
	 * 创建对话框
	 * 
	 * @author pumkid
	 */
	private void createDialog() {
		rellp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		if (dl == DIALOGTYPE.users) {
			if (descButtons == null || descButtons.size() < 1)
				return;
			relButtons = new ArrayList<RelativeLayout>();
			RelativeLayout itemButton;
			winWidth -= descButtons.size() - 1;
			int weight = winWidth / descButtons.size();
			rellp.width = weight;
			tags = descButtons;
			for (int i = 0; i < descButtons.size(); i++) {

				itemButton = new RelativeLayout(context);
				itemButton.setGravity(Gravity.CENTER);
				itemButton.setOnClickListener(listener);
				itemButton.setTag(descButtons.get(i));
				itemButton.setBackgroundResource(R.drawable.bg_dialog_btn);
				TextView tvText = createTextView(descButtons.get(i), rellp,
						context, this.colorContentText);
				itemButton.addView(tvText);

				tvText.setTextSize(Constants.CONTENTSIZE - descButtons.size()
						+2);
				// 可以加图片
				// .
				// .
				// .

				ll_buttonList.addView(itemButton);
				ll_buttonList.addView(createDivider(context));
			}
		} else if (dl == DIALOGTYPE.defualt) {
			RelativeLayout itemButton;
			int weight = winWidth / 2;
			rellp.width = weight;
			tags = new ArrayList<String>();
			for (int i = 0; i < 2; i++) {
				String itemstr = i > 0 ? resources.getString(strConfirm)
						: resources.getString(strCancel);
				itemButton = new RelativeLayout(context);
				itemButton.setGravity(Gravity.CENTER);
				itemButton.setOnClickListener(listener);
				itemButton.setBackgroundResource(R.drawable.bg_dialog_btn);
				itemButton.setClickable(true);
				itemButton.setTag(itemstr);
				TextView item = createTextView(itemstr, rellp, context,
						this.colorContentText);
				itemButton.addView(item);
				tags.add(itemstr);

				ll_buttonList.addView(itemButton);
				ll_buttonList.addView(createDivider(context));
			}
		}
	}

	private View createDivider(Context context) {
		RelativeLayout.LayoutParams rellp_l = new RelativeLayout.LayoutParams(
				1, LayoutParams.MATCH_PARENT);
		View line = new View(context);
		line.setBackgroundColor(this.resources.getColor(R.color.common_line));
		line.setLayoutParams(rellp_l);
		return line;
	}

	/**
	 * 创建一个TextView
	 * 
	 * @param btnName
	 * @param lp
	 * @param context
	 * @return
	 */
	private TextView createTextView(String btnName, LayoutParams lp,
			Context context, int recolor) {
		TextView tvText = new TextView(context);
		tvText.setGravity(Gravity.CENTER);
		tvText.setText(btnName);
		tvText.setLayoutParams(lp);
		Log.d("this.resources.getDimension(R.dimen.common_text_size) "
				+ this.resources.getDimension(R.dimen.common_text_size));
		tvText.setTextSize(Constants.CONTENTSIZE);
		if (recolor == -1)
			tvText.setTextColor(this.resources.getColor(this.colorContentText));
		else
			tvText.setTextColor(this.resources.getColor(recolor));
		return tvText;
	}

	public void setDialogType(DIALOGTYPE type) {
		this.dl = type;
	}

	private void setTitle() {
		tv_messagetitle = (TextView) this.findViewById(R.id.tv_messagetitle);
		tv_messagetitle.setTextColor(this.resources
				.getColor(this.colorTitleText));
		tv_messagetitle.setText(this.dialogtitle);
	}

	public void setContent() {
		rel_messagecontent = (RelativeLayout) this
				.findViewById(R.id.rel_messagecontent);
		for (int i = 0; i < contents.length; i++) {

			TextView info = createTextView(contents[i],
					rel_messagecontent.getLayoutParams(), context,
					this.colorContentText);
			info.setGravity(Gravity.LEFT);
			rel_messagecontent.addView(info);
		}
	}

	public void setContent(RelativeLayout re) {

	}

	public android.view.View.OnClickListener getListener() {
		return listener;
	}

	public void setListener(android.view.View.OnClickListener listener) {
		this.listener = listener;
	}

	public String getDialogtitle() {
		return dialogtitle;
	}

	public void setDialogtitle(String dialogtitle) {
		this.dialogtitle = dialogtitle;
	}

	public String[] getContents() {
		return contents;
	}

	public void setContents(String[] contents) {
		this.contents = contents;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public int getTagsSize() {
		if (tags == null)
			return 0;

		return this.tags.size();
	}

	public ArrayList<String> getDescButtons() {
		return descButtons;
	}

	public void setDescButtons(ArrayList<String> descButtons) {
		this.descButtons = descButtons;
	}

	/**
	 * 定义窗口类型
	 * 
	 * @author pumkid
	 *
	 */
	public enum DIALOGTYPE {
		defualt, users, systems
	}

}
