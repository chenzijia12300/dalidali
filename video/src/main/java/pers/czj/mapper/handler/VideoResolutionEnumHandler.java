/*
package pers.czj.mapper.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import pers.czj.constant.VideoResolutionEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

*/
/**
 * 创建在 2020/7/13 22:43
 *//*

@MappedTypes(VideoResolutionEnum.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class VideoResolutionEnumHandler extends BaseTypeHandler<VideoResolutionEnum> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, VideoResolutionEnum videoResolutionEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i,videoResolutionEnum.getCode());
    }

    @Override
    public VideoResolutionEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return VideoResolutionEnum.getInstance();
    }

    @Override
    public VideoResolutionEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public VideoResolutionEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
*/
