package com.heckaitor.demo;

import com.heckaitor.demo.contents.PermissionActivity;
import com.heckaitor.demo.contents.asyncdialog.AsyncTaskActivity;
import com.heckaitor.demo.views.ImageScaleActivity;
import com.heckaitor.demo.views.bezier.BezierActivity;
import com.heckaitor.demo.views.interceptradio.InterceptRadioActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 所有demo的入口配置
 *
 * Created by kaige1 on 2017/5/25.
 */
public class Config {
    
    enum Category {
        VIEW,
        CONTENT,
        TOOL
    }
    
    /**
     * Demo信息
     * Created by kaige1 on 2017/5/25.
     */
    static class DemoDesc {
        
        public DemoDesc(String title, String desc, Class target) {
            this.title = title;
            this.desc = desc;
            this.target = target;
        }
        
        String title;  //标题
        String desc;   //简介
        Class target;  //目标Activity，demo入口
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
        final List viewDemos = new ArrayList();
        viewDemos.add(new DemoDesc("Bezier曲线", "通过一组固定的点绘制bezier曲线", BezierActivity.class));
        viewDemos.add(new DemoDesc("Intercept RadioButton", "在普通单选按钮的基础上，增加了选项变更的拦截操作", InterceptRadioActivity.class));
        viewDemos.add(new DemoDesc("ImageView.ScaleType", "实际体验ImageView设置不同的scaleType的效果", ImageScaleActivity.class));
        mEntrances.put(Category.VIEW, viewDemos);
        
        
        final List contentDemos = new ArrayList();
        contentDemos.add(new DemoDesc("AlterDialog vs DialogFragment", "在异步任务中更新UI，验证使用Fragment的一些错误用法", AsyncTaskActivity.class));
        contentDemos.add(new DemoDesc("Runtime Permission", "运行时动态获取权限", PermissionActivity.class));
        mEntrances.put(Category.CONTENT, contentDemos);
        
        
        final List toolDemos = new ArrayList();
        mEntrances.put(Category.TOOL, toolDemos);
    }
    
}
