package com.xx.vote.interceptor;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xx.common.PageData;
import com.xx.common.ResultData;
import com.xx.utils.PageDataUtil;
import com.xx.utils.ServletUtil;
import com.xx.utils.ValidationUtil;
import com.xx.utils.ValueUtil;
import com.xx.vote.annotation.NeedPage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: xueqimiao
 * @Date: 2022/10/17 14:30
 */
//@Component
@Aspect
public class PageDataInterceptor {

    public Logger log = LoggerFactory.getLogger(PageDataInterceptor.class);

    //使用线程本地变量
    //private static final ThreadLocal<PageBean> pageBeanContext = new ThreadLocal<>();

    @Pointcut("@annotation(com.xx.vote.annotation.NeedPage)")
    public void controllerAspect() {
    }

    @Around("controllerAspect()")
    public ResultData controllerAop(ProceedingJoinPoint proJoinPoint) throws Throwable {
        log.info("==========> controller正在执行PageHelperAop");
        HttpServletRequest request = ServletUtil.getHttpRequest();
        int currentPage = getIntParameter(request, PageDataUtil.PAGE_CURRENT_PAGE_NO_STR, 1);
        int pageSize = getIntParameter(request, PageDataUtil.PAGE_SIZE_STR, 10);
        PageBean pageBean = new PageBean();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        //将分页参数放置线程变量中
        //pageBeanContext.set(pageBean);
        Page<Object> page = PageHelper.startPage(pageBean.getCurrentPage(), pageBean.getPageSize());

        return handlePage(proJoinPoint, page);
    }

    private ResultData handlePage(ProceedingJoinPoint proJoinPoint, Page<Object> page) throws Throwable {
        MethodSignature methodSign = (MethodSignature) proJoinPoint.getSignature();
        NeedPage needPageAnno = methodSign.getMethod().getAnnotation(NeedPage.class);
        Object retVal = proJoinPoint.proceed();
        if ((ValidationUtil.isEmpty(needPageAnno)) ||
                retVal == null || !(retVal instanceof ResultData)
                || !((ResultData) retVal).isSuccess()) {
            return new ResultData(retVal);
        }
        ResultData returnResult = (ResultData) retVal;
        if (!(returnResult.getData() instanceof List)) {
            return returnResult;
        }
        //List resultList = (List) returnResult.getData();
        //if(ValidationUtil.isEmpty(resultList)){
        //    return new ResultData(resultList);
        //}
        PageInfo pageInfo = new PageInfo<>(page);
        PageData pageData = ValueUtil.copyFieldValue(pageInfo, PageData.class);
        returnResult.setPageData(pageData);
        return returnResult;
    }


    private static int getIntParameter(HttpServletRequest request, String paramName, Integer defaultVal) {
        String paramValue = request.getParameter(paramName);
        if (ValidationUtil.isEmpty(paramValue)) {
            return defaultVal;
        }
        return Integer.parseInt(paramValue);
    }
}
