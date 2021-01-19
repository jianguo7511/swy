package com.apiyoo.anthorization.swy.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("authorization")
public class Authorization implements Serializable {
        @TableId(type = IdType.UUID)
        private String aid;
        private String authorid;

        @NotBlank(message = "【企业Id】不能为空.")
        private String orgid;

        @NotBlank(message = "【企业名称】不能为空.")
        private String org_name;
}
