package com.wesmarclothing.jniproject.blockcanary;

import android.content.Context;
import android.util.Log;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class AppBlockCanaryContext extends BlockCanaryContext {
    // 实现各种上下文，包括应用标示符，用户uid，网络类型，卡慢判断阙值，Log保存位置等

    /**
     * Implement in your project.
     * 限定符，可以指定此安装，比如version + flavor。
     *
     * @return Qualifier which can specify this installation, like version + flavor.
     */
    public String provideQualifier() {
        return "unknown";
    }

    /**
     * Implement in your project.
     *
     * @return user id
     */
    public String provideUid() {
        return "uid";
    }

    /**
     * Network type
     *
     * @return {@link String} like 2G, 3G, 4G, wifi, etc.
     */
    public String provideNetworkType() {
        return "unknown";
    }

    /**
     * 配置监视器持续时间，在此时间后BlockCanary将停止
     * <p>
     * Config monitor duration, after this time BlockCanary will stop, use
     * with {@code BlockCanary}'s isMonitorDurationEnd
     *
     * @return monitor last duration (in hour)
     */
    public int provideMonitorDuration() {
        return -1;
    }

    /**
     * 配置块阈值(在millis中)，在此期间的分派被视为块。你可以把它调好
     * 从设备性能来看。
     * <p>
     * Config block threshold (in millis), dispatch over this duration is regarded as a BLOCK. You may set it
     * from performance of device.
     *
     * @return threshold in mills
     */
    public int provideBlockThreshold() {
        return 500;
    }

    /**
     * 线程堆栈转储间隔，当发生阻塞时使用，BlockCanary将在主线程上转储
     * *按当前样品周期堆叠。
     * <p>
     * Thread stack dump interval, use when block happens, BlockCanary will dump on main thread
     * stack according to current sample cycle.
     * <p>
     * Because the implementation mechanism of Looper, real dump interval would be longer than
     * the period specified here (especially when cpu is busier).
     * </p>
     *
     * @return dump interval (in millis)
     */
    public int provideDumpInterval() {
        return provideBlockThreshold();
    }

    /**
     * Path to save log, like "/blockcanary/", will save to sdcard if can.
     *
     * @return path of log files
     */
    public String providePath() {
        return "/blockcanary/";
    }

    /**
     * If need notification to notice block.
     *
     * @return true if need, else if not need.
     */
    public boolean displayNotification() {
        return true;
    }

    /**
     * 在项目中实现，将文件打包到zip文件中。
     * <p>
     * Implement in your project, bundle files into a zip file.
     *
     * @param src  files before compress
     * @param dest files compressed
     * @return true if compression is successful
     */
    public boolean zip(File[] src, File dest) {
        return false;
    }

    /**
     * 在项目中实现绑定的日志文件。
     * Implement in your project, bundled log files.
     *
     * @param zippedFile zipped file
     */
    public void upload(File zippedFile) {
        throw new UnsupportedOperationException();
    }


    /**
     * 默认情况下，它使用进程名，
     * Packages that developer concern, by default it uses process name,
     * put high priority one in pre-order.
     *
     * @return null if simply concern only package with process name.
     */
    public List<String> concernPackages() {
        return null;
    }

    /**
     * 过滤器堆栈中没有任何关注包，使用
     * Filter stack without any in concern package, used with @{code concernPackages}.
     *
     * @return true if filter, false it not.
     */
    public boolean filterNonConcernStack() {
        return false;
    }

    /**
     * 提供白列表，白列表中的条目将不会显示在ui列表中
     * Provide white list, entry in white list will not be shown in ui list.
     *
     * @return return null if you don't need white-list filter.
     */
    public List<String> provideWhiteList() {
        LinkedList<String> whiteList = new LinkedList<>();
        whiteList.add("org.chromium");
        return whiteList;
    }

    /**
     * 是否删除堆栈在白列表中的文件，与白列表一起使用。
     * Whether to delete files whose stack is in white list, used with white-list.
     *
     * @return true if delete, false it not.
     */
    public boolean deleteFilesInWhiteList() {
        return true;
    }

    /**
     * 块拦截器，开发人员可以提供自己的动作。
     * Block interceptor, developer may provide their own actions.
     */
    public void onBlock(Context context, BlockInfo blockInfo) {
        Log.e("BlockInfo", blockInfo.toString());
    }
}