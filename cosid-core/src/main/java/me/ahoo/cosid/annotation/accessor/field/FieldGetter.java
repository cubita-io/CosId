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

package me.ahoo.cosid.annotation.accessor.field;

import me.ahoo.cosid.CosIdException;
import me.ahoo.cosid.annotation.CosIdDefinition;
import me.ahoo.cosid.annotation.accessor.AbstractIdMetadata;
import me.ahoo.cosid.annotation.accessor.CosIdAccessor;
import me.ahoo.cosid.annotation.accessor.CosIdGetter;

import java.lang.reflect.Field;

/**
 * @author ahoo wang
 */
public class FieldGetter extends AbstractIdMetadata implements CosIdGetter {

    public FieldGetter(CosIdDefinition cosIdDefinition, Field field) {
        super(cosIdDefinition, field);
        CosIdAccessor.ensureAccessible(field);
    }

    @Override
    public Object get(Object target) {
        try {
            return getIdField().get(target);
        } catch (IllegalAccessException e) {
            throw new CosIdException(e.getMessage(), e);
        }
    }
}
