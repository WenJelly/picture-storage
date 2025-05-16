package com.wenjelly.smartpicturestorage.api.aliyunai.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOutPaintingTaskResponse {

    private Output output;
    /**
     * 接口错误码。
     * 接口成功请求不会返回该参数。
     */
    private String code;
    /**
     * 接口错误信息。
     * 接口成功请求不会返回该参数。
     */
    private String message;
    /**
     * 请求唯一标识。
     * 可用于请求明细溯源和问题排查。
     */
    private String requestId;

    /**
     * 表示任务的输出信息
     */
    @Data
    public static class Output {

        /**
         * 任务 ID
         */
        private String taskId;

        /**
         * 任务状态
         * PENDING：排队中
         * RUNNING：处理中
         * SUSPENDED：挂起
         * SUCCEEDED：执行成功
         * FAILED：执行失败
         * UNKNOWN：任务不存在或状态未知
         */
        private String taskStatus;
    }

}
