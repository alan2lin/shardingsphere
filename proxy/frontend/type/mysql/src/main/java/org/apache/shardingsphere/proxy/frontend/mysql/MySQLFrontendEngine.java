/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.proxy.frontend.mysql;

import io.netty.channel.Channel;
import lombok.Getter;
import org.apache.shardingsphere.db.protocol.codec.DatabasePacketCodecEngine;
import org.apache.shardingsphere.db.protocol.mysql.codec.MySQLPacketCodecEngine;
import org.apache.shardingsphere.db.protocol.mysql.constant.MySQLConstants;
import org.apache.shardingsphere.db.protocol.mysql.constant.MySQLServerInfo;
import org.apache.shardingsphere.db.protocol.mysql.netty.MySQLSequenceIDInboundHandler;
import org.apache.shardingsphere.db.protocol.mysql.packet.MySQLPacket;
import org.apache.shardingsphere.proxy.backend.session.ConnectionSession;
import org.apache.shardingsphere.proxy.frontend.authentication.AuthenticationEngine;
import org.apache.shardingsphere.proxy.frontend.command.CommandExecuteEngine;
import org.apache.shardingsphere.proxy.frontend.mysql.authentication.MySQLAuthenticationEngine;
import org.apache.shardingsphere.proxy.frontend.mysql.command.MySQLCommandExecuteEngine;
import org.apache.shardingsphere.proxy.frontend.mysql.command.query.binary.MySQLStatementIDGenerator;
import org.apache.shardingsphere.proxy.frontend.netty.FrontendChannelInboundHandler;
import org.apache.shardingsphere.proxy.frontend.spi.DatabaseProtocolFrontendEngine;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Frontend engine for MySQL.
 */
@Getter
public final class MySQLFrontendEngine implements DatabaseProtocolFrontendEngine {
    
    private final AuthenticationEngine authenticationEngine = new MySQLAuthenticationEngine();
    
    private final CommandExecuteEngine commandExecuteEngine = new MySQLCommandExecuteEngine();
    
    private final DatabasePacketCodecEngine<MySQLPacket> codecEngine = new MySQLPacketCodecEngine();
    
    @Override
    public void initChannel(final Channel channel) {
        channel.attr(MySQLConstants.MYSQL_SEQUENCE_ID).set(new AtomicInteger());
        channel.pipeline().addBefore(FrontendChannelInboundHandler.class.getSimpleName(), MySQLSequenceIDInboundHandler.class.getSimpleName(), new MySQLSequenceIDInboundHandler());
    }
    
    @Override
    public void setDatabaseVersion(final String databaseName, final String databaseVersion) {
        MySQLServerInfo.setServerVersion(databaseName, databaseVersion);
    }
    
    @Override
    public void release(final ConnectionSession connectionSession) {
        MySQLStatementIDGenerator.getInstance().unregisterConnection(connectionSession.getConnectionId());
    }
    
    @Override
    public void handleException(final ConnectionSession connectionSession, final Exception exception) {
    }
    
    @Override
    public String getType() {
        return "MySQL";
    }
}
