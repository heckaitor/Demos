package com.heckaitor.demo.image;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.heckaitor.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * {@link ImageView.ScaleType}实践
 */
public class ImageScaleActivity extends AppCompatActivity {

    @BindView(R.id.iv_big_even) ImageView mBigEvenView;
    @BindView(R.id.iv_big_fat) ImageView mBigFatView;
    @BindView(R.id.iv_big_thin) ImageView mBigThinView;
    @BindView(R.id.iv_small_even) ImageView mSmallEvenView;
    @BindView(R.id.iv_small_fat) ImageView mSmallFatView;
    @BindView(R.id.iv_small_thin) ImageView mSmallThinView;
	
    @BindView(R.id.typeView) TextView mTypeView;
    @BindView(R.id.descView) TextView mDescView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_scale);
        ButterKnife.bind(this);

		// ImageView的默认scale type：fitCenter
		updateScaleType(ImageView.ScaleType.FIT_CENTER);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.image_scale, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		final int id = item.getItemId();
		if (R.id.action_matrix == id) {
			updateScaleType(ImageView.ScaleType.MATRIX);
			return true;
		} else if (R.id.action_fitXY == id) {
			updateScaleType(ImageView.ScaleType.FIT_XY);
			return true;
		} else if (R.id.action_fitStart == id) {
			updateScaleType(ImageView.ScaleType.FIT_START);
			return true;
		} else if (R.id.action_fitCenter == id) {
			updateScaleType(ImageView.ScaleType.FIT_CENTER);
			return true;
		} else if (R.id.action_fitEnd == id) {
			updateScaleType(ImageView.ScaleType.FIT_END);
			return true;
		} else if (R.id.action_center == id) {
			updateScaleType(ImageView.ScaleType.CENTER);
			return true;
		} else if (R.id.action_centerCrop == id) {
			updateScaleType(ImageView.ScaleType.CENTER_CROP);
			return true;
		} else if (R.id.action_centerInside == id) {
			updateScaleType(ImageView.ScaleType.CENTER_INSIDE);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void updateScaleType(ImageView.ScaleType type) {
		mBigEvenView.setScaleType(type);
		mBigFatView.setScaleType(type);
		mBigThinView.setScaleType(type);
        
        mSmallEvenView.setScaleType(type);
        mSmallFatView.setScaleType(type);
        mSmallThinView.setScaleType(type);

		mTypeView.setText(type.toString());
		updateDesc(type);
	}

	private void updateDesc(ImageView.ScaleType type) {
		String text;
		switch (type) {
			case MATRIX:
				text = "结合matrix对图片进行变幻";
				break;
			case FIT_XY:
				text = "不按比例缩放图片，目标是把图片塞满整个View";
				break;
			case FIT_START:
				text = "把图片按比例扩大/缩小到View的宽度，从顶部开始显示";
				break;
			case FIT_CENTER:
				text = "把图片按比例扩大/缩小到View的宽度，居中显示";
				break;
			case FIT_END:
				text = "把图片按比例扩大/缩小到View的宽度，从底部开始显示";
				break;
			case CENTER:
				text = "按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截取图片的居中部分显示";
				break;
			case CENTER_CROP:
				text = "按比例扩大图片的尺寸，居中显示，使得图片长(宽)等于或大于View的长(宽) ";
				break;
			case CENTER_INSIDE:
				text = "将图片的内容完整居中显示，通过按比例缩小原来的尺寸使得图片长/宽等于或小于View的长/宽 ";
				break;
			default:
				text = "";
				break;
		}

		mDescView.setText(text);
	}
}
