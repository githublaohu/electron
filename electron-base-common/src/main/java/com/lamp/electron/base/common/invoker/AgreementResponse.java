/*
 *Copyright (c) [Year] [name of copyright holder]
 *[Software Name] is licensed under Mulan PubL v2.
 *You can use this software according to the terms and conditions of the Mulan PubL v2.
 *You may obtain a copy of Mulan PubL v2 at:
 *         http://license.coscl.org.cn/MulanPubL-2.0
 *THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 *EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 *MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 *See the Mulan PubL v2 for more details.
 */
package com.lamp.electron.base.common.invoker;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.Charset;
import java.util.Objects;

public interface AgreementResponse {

	public void reply(ElectronResponse electronResponse, ElectronRequest electronRequest);

    default ByteBuf createConnectByteBuf(Object connect) {
        if(Objects.isNull(connect)) {
            return Unpooled.EMPTY_BUFFER;
        }
        if(connect instanceof String) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeCharSequence((String)connect, Charset.defaultCharset());
            return buf;
        }
        return (ByteBuf)connect;

    }
}
