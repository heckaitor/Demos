package com.heckaitor.demo;

import com.heckaitor.demo.anim.AlphaControlActivity;
import com.heckaitor.demo.autoplay.AutoPlayActivity;
import com.heckaitor.demo.canvas.DrawActivity;
import com.heckaitor.demo.pager.PagerActivity2;
import com.heckaitor.demo.permission.PermissionActivity;
import com.heckaitor.demo.asyncdialog.AsyncTaskActivity;
import com.heckaitor.demo.popw.PopupWindowActivity;
import com.heckaitor.demo.touch.TouchDelegateActivity;
import com.heckaitor.demo.bezier.BezierActivity;
import com.heckaitor.demo.image.ImageScaleActivity;
import com.heckaitor.demo.list.ListViewActivity;
import com.heckaitor.demo.recycler.RecyclerViewActivity;
import com.heckaitor.demo.coord.ViewCoordinateActivity;
import com.heckaitor.demo.interceptradio.InterceptRadioActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有demo的入口配置
 *
 * Created by heckaitor on 2017/5/25.
 */
public class Config {
    
    public enum Category {
        CONTENT,
        VIEW,
        ANIMATES,
        TOOL
    }
    
    /**
     * Demo信息
     * Created by heckaitor on 2017/5/25.
     */
    public static class DemoDesc {
        
        public DemoDesc(String title, String desc, Class target) {
            this.title = title;
            this.desc = desc;
            this.target = target;
        }
        
        public final String title;  //标题
        public final String desc;   //简介
        public final Class target;  //目标Activity，demo入口
    }
    
    private static Map<Category, List<DemoDesc>> mEntrances = new HashMap<>();
    
    /**
     * 按类别获取列表
     * @param category
     * @return
     */
    public static List<DemoDesc> getList(Category category) {
        return mEntrances.get(category);
    }
    
    static {
        final List contentDemos = new ArrayList();
        contentDemos.add(new DemoDesc("AlterDialog vs DialogFragment", "在异步任务中更新UI，验证使用Fragment的一些错误用法", AsyncTaskActivity.class));
        contentDemos.add(new DemoDesc("Runtime Permission", "运行时动态获取权限", PermissionActivity.class));
        contentDemos.add(new DemoDesc("PopupWindow", "PopupWindow的坑", PopupWindowActivity.class));
        contentDemos.add(new DemoDesc("RecyclerView", "疑难杂症", RecyclerViewActivity.class));
        contentDemos.add(new DemoDesc("ListView", "疑难杂症", ListViewActivity.class));
        contentDemos.add(new DemoDesc("自动播放", "模拟视频流自动播放机制", AutoPlayActivity.class));
        mEntrances.put(Category.CONTENT, contentDemos);


        final List viewDemos = new ArrayList();
        viewDemos.add(new DemoDesc("Draw", "Canvas画图", DrawActivity.class));
        viewDemos.add(new DemoDesc("Bezier曲线", "通过一组固定的点绘制bezier曲线", BezierActivity.class));
        viewDemos.add(new DemoDesc("Intercept RadioButton", "在普通单选按钮的基础上，增加了选项变更的拦截操作", InterceptRadioActivity.class));
        viewDemos.add(new DemoDesc("ImageView.ScaleType", "实际体验ImageView设置不同的scaleType的效果", ImageScaleActivity.class));
        viewDemos.add(new DemoDesc("View Coordinate", "View坐标系", ViewCoordinateActivity.class));
        viewDemos.add(new DemoDesc("TouchDelegate", "一般用于在不改变布局的情况下，扩大View的点击区域，但并不常用，而且使用过程中会有一些容易被忽略的问题", TouchDelegateActivity.class));
        viewDemos.add(new DemoDesc("ViewPager", "ViewPager的用法", PagerActivity2.class));
        mEntrances.put(Category.VIEW, viewDemos);

        final List animateDemos = new ArrayList();
        animateDemos.add(new DemoDesc("动画控制", "", AlphaControlActivity.class));
        mEntrances.put(Category.ANIMATES, animateDemos);

        final List toolDemos = new ArrayList();
        mEntrances.put(Category.TOOL, toolDemos);
    }
    
}
