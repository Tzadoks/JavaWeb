package com.tzadok.imperial.court.dao.impl;

import com.tzadok.imperial.court.dao.BaseDao;
import com.tzadok.imperial.court.dao.api.MemorialsDao;
import com.tzadok.imperial.court.entity.Memorials;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ProjectName: pro05-demo-all-in-one
 * @PackageName: com.tzadok.imperial.court.dao.impl
 * @ClassName: MemorialsDaoImpl
 * @Author: 小北
 * @Description:
 * @date: 2022/7/31 16:09
 */
public class MemorialsDaoImpl extends BaseDao<Memorials> implements MemorialsDao {
    @Override
    public List<Memorials> selectAllMemorialsDigest() {

        String sql = "select memorials_id memorialsId,\n" +
                "       memorials_title memorialsTitle,\n" +
                "       concat(left(memorials_content,10),\"...\") memorialsContentDigest,\n" +
                "       emp_name memorialsEmpName,\n" +
                "       memorials_create_time memorialsCreateTime,\n" +
                "       memorials_status memorialsStatus\n" +
                "from  t_memorials m left join t_emp e on memorials_emp = emp_id;";

        return getBeanList(sql, Memorials.class);
    }

    @Override
    public Memorials selectMemorialsById(String memorialsId) {

        String sql = "select memorials_id memorialsId,\n" +
                "       memorials_title memorialsTitle,\n" +
                "       memorials_content memorialsContent,\n" +
                "       emp_name memorialsEmpName,\n" +
                "       memorials_create_time memorialsCreateTime,\n" +
                "       memorials_status memorialsStatus,\n" +
                "       feedback_time feedbackTime,\n" +
                "       feedback_content feedbackContent\n" +
                "from t_memorials m left join  t_emp e on m.memorials_emp=e.emp_id " +
                "where memorials_id=?";

        return getSingleBean(sql, Memorials.class, memorialsId);
    }

    @Override
    public void updateMemorialsStatusToRead(String memorialsId) {
        String sql = "update t_memorials set memorials_status = 1 where memorials_id=?";

        update(sql,memorialsId);
    }

    @Override
    public void updateMemorialsFeedBack(String memorialsId, String feedbackContent) {

        String feedbackTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sql = "update t_memorials set memorials_status = 2,feedback_content=?,feedback_time=? where memorials_id=?";

        update(sql,feedbackContent,feedbackTime,memorialsId);

    }

}