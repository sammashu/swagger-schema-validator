/*
 * Copyright @ 2016 Atlassian Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.bjansen.ssv.formats;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.msgsimple.bundle.MessageBundle;

public final class FloatAttribute extends AbstractFormatAttribute {

    private static final FormatAttribute INSTANCE = new FloatAttribute();

    public static FormatAttribute getInstance() {
        return INSTANCE;
    }

    private FloatAttribute() {
        super("float", NodeType.NUMBER);
    }

    @Override
    public void validate(final ProcessingReport report,
                         final MessageBundle bundle,
                         final FullData data) throws ProcessingException {
        final JsonNode instance = data.getInstance().getNode();

        final float f = instance.floatValue();
        final String original = String.valueOf(instance.decimalValue());
        final String parsed = String.valueOf(f);

        if (!original.equals(parsed)) {
            report.warn(newMsg(data, bundle, "warn.format.float.overflow")
                .put("key", "warn.format.float.overflow")
                .putArgument("value", original)
                .putArgument("converted", parsed));
        }
    }
}
