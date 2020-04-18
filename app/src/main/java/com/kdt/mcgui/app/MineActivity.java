package com.kdt.mcgui.app;

import android.content.pm.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.*;
import net.kdt.pojavlaunch.*;

public class MineActivity extends AppCompatActivity implements View.OnClickListener
{
	private int topId = 150001;
	private boolean showBeforeView = true;
	
	private FontChanger fontChanger;
	
	private ImageButton menu;
	private LinearLayout content, undertop;

	private LayoutInflater li;
	
	public ViewGroup replaceFonts(ViewGroup viewTree)
	{
		if (fontChanger == null) fontChanger = new FontChanger(getAssets(), "font/NotoSans-Bold.ttf");
		return fontChanger.replaceFonts(viewTree);
	}
	
	public View replaceFont(TextView view)
	{
		if (fontChanger == null) fontChanger = new FontChanger(getAssets(), "font/NotoSans-Bold.ttf");
		return fontChanger.replaceFont(view);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		this.onCreate(savedInstanceState, true);
	}
	
	protected void onCreate(Bundle savedInstanceState, boolean showBeforeView)
	{
		super.onCreate(savedInstanceState);
		
		this.showBeforeView = showBeforeView;
		if (showBeforeView) {
			mcUIInit();
		}
	}
	
	private void mcUIInit() {
		RelativeLayout root = new RelativeLayout(this);
		LinearLayout top = new LinearLayout(this);
		top.setId(topId);

		content = new LinearLayout(this);
		RelativeLayout btm = new RelativeLayout(this);

		li = LayoutInflater.from(this);
		li.inflate(R.layout.top_bar, top, true);
		li.inflate(R.layout.bottom_bar, btm, true);
		
		replaceFonts(btm);

		replaceFont((TextView) top.findViewById(R.id.topbar_language_text));

		RelativeLayout.LayoutParams conLay = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
		conLay.addRule(root.BELOW, topId);
		conLay.bottomMargin = 66;

		content.setLayoutParams(conLay);

		root.addView(top, new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		root.addView(content);
		root.addView(btm);

		super.setContentView(root);

		TextView ver = (TextView) findViewById(R.id.bottombar_version_view);

		menu = (ImageButton) findViewById(R.id.topbar_navmenu_icon);
		setMenuVisible(false);
		
		undertop = (LinearLayout) findViewById(R.id.topbar_undertop_view);

		try {
			ver.setText(getPackageManager().getPackageInfo(getPackageName(), 0).versionName);
		} catch (PackageManager.NameNotFoundException e) {} // Never happend!

		setClick(R.id.topbar_help_text);
		setClick(R.id.topbar_logo);
		setClick(R.id.bottombar_author_logo);
	}
	
	@Override
	public void setContentView(int resource)
	{
		if (!showBeforeView) {
			mcUIInit();
		}
		
		li.inflate(resource, content, true);
		replaceFonts(content);
	}

	@Override
	public void setContentView(View view)
	{
		if (!showBeforeView) {
			mcUIInit();
		}
		
		content.addView(view);
		if (view instanceof ViewGroup) {
			replaceFonts((ViewGroup) view);
		}
	}
	
	@Override
	public void onClick(View view)
	{
		switch (view.getId()) {
			case R.id.topbar_help_text: Tools.openURL(this, "https://www.minecraft.net/help");
				break;
			case R.id.topbar_logo: Tools.openURL(this, "https://www.minecraft.net");
				break;
			case R.id.bottombar_author_logo: Tools.openURL(this, "https://mojang.com");
				break;
		}
	}
	
	public void setUndertopView(View view) {
		if (undertop.getChildCount() > 0) {
			undertop.removeAllViews();
		}
		undertop.addView(view);
	}
	
	public void setMenuVisible(boolean value) {
		menu.setVisibility(value ? View.VISIBLE : View.GONE);
	}
	
	public void setClick(int id) {
		findViewById(id).setOnClickListener(this);
	}
}
