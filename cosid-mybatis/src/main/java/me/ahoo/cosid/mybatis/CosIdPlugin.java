/*
 * Copyright [2021-present] [ahoo wang <ahoowang@qq.com> (https://github.com/Ahoo-Wang)].
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.ahoo.cosid.mybatis;


import me.ahoo.cosid.annotation.CosIdAnnotationSupport;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Collection;
import java.util.Map;

/**
 * @author ahoo wang
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class CosIdPlugin implements Interceptor {

    public static final String DEFAULT_LIST_KEY = "list";
    private final CosIdAnnotationSupport cosIdSupport;

    public CosIdPlugin(CosIdAnnotationSupport cosIdSupport) {
        this.cosIdSupport = cosIdSupport;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        Object[] args = invocation.getArgs();
        MappedStatement statement = (MappedStatement) args[0];
        if (!SqlCommandType.INSERT.equals(statement.getSqlCommandType())) {
            return invocation.proceed();
        }

        Object parameter = args[1];
        if (!(parameter instanceof Map)) {
            cosIdSupport.ensureId(parameter);
            return invocation.proceed();
        }

        Collection entityList = (Collection) ((Map) parameter).get(DEFAULT_LIST_KEY);

        for (Object entity : entityList) {
            cosIdSupport.ensureId(entity);
        }
        return invocation.proceed();
    }
}
