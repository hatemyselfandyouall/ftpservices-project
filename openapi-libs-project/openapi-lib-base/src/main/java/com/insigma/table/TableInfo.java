package com.insigma.table;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors(chain = true)
public class TableInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String TableName;

    private String TableRemark;

    private Integer totalCount;

    private List<FieldInfo> fieldInfos;

    private List<JSONObject> tableDatas;
}
