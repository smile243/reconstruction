package org.yjl.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;
import org.yjl.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Tag(name = "测试DEMO对象")
@TableName("demo")
public class Demo extends BaseEntity {

    @Schema(description = "姓名")
    private String name;
    /**
     * 关键词
     */
    @Schema(description = "关键词")
    private String keyWord;
    /**
     * 打卡时间
     */
    @Schema(description = "打卡时间")
    private Date punchTime;
    /**
     * 工资
     */
    @Schema(description = "工资", example = "0")
    private BigDecimal salaryMoney;
    /**
     * 奖金
     */
    @Schema(description = "奖金", example = "0")
    private Double bonusMoney;
    /**
     * 性别 {男:1,女:2}
     */
    @Schema(description = "性别")
    private String sex;
    /**
     * 年龄
     */
    @Schema(description = "年龄", example = "0")
    private Integer age;
    /**
     * 生日
     */
    @Schema(description = "生日")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;
    /**
     * 个人简介
     */
    @Schema(description = "个人简介")
    private String content;
    @Schema(description = "租户ID")
    private Integer tenantId;
    /**
     * 部门编码
     */
    @Schema(description = "部门编码")
    private String sysOrgCode;
}
