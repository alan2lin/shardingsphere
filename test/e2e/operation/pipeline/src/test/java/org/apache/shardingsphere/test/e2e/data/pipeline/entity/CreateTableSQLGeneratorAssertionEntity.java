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

package org.apache.shardingsphere.test.e2e.data.pipeline.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.Collection;

/**
 * Create table sql generator entity for JAXB.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@Setter
public final class CreateTableSQLGeneratorAssertionEntity {
    
    @XmlAttribute(required = true)
    private String id;
    
    @XmlElement(required = true)
    private CreateTableSQLGeneratorInputEntity input;
    
    @XmlElement(required = true, name = "output")
    private Collection<CreateTableSQLGeneratorOutputEntity> outputs;
}
