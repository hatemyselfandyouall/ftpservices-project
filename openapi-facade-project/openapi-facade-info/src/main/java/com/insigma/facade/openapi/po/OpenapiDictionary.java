 
package com.insigma.facade.openapi.po;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.persistence.*;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import java.util.List;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class OpenapiDictionary implements Serializable{


	//========== properties ==========

	@Id
	@GeneratedValue(generator="JDBC")
    @ApiModelProperty("")
    @Column( name="id")
    private Long id;

    @ApiModelProperty("")
    @Column( name="code")
    private String code;

    @ApiModelProperty("")
    @Column( name="value")
    private String value;

    @ApiModelProperty("")
    @Column( name="remark")
    private String remark;

    @ApiModelProperty("")
    @Column( name="parent_id")
    private Long parentId;

    @ApiModelProperty("")
    @Column( name="tree_path")
    private String treePath;




}
