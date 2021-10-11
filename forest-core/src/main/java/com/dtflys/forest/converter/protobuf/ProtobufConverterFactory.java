/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Jun Gong
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.dtflys.forest.converter.protobuf;

import com.dtflys.forest.exceptions.ForestRuntimeException;

import java.io.Serializable;

/**
 * JSON转换器选择策略
 *
 * @author YAKAX
 * @since 2020/12/18
 **/
public class ProtobufConverterFactory implements Serializable {

    private static ProtobufConverterFactory instance;
    private final static String PROTOBUF_CONVERTER_CLASS = "com.dtflys.forest.converter.protobuf.ForestGoogleProtobufConverter";

    private ForestProtobufConverter forestProtobufConverter;

    public static ProtobufConverterFactory getInstance() {
        if (instance != null) {
            return instance;
        }
        instance = new ProtobufConverterFactory();
        return instance;
    }


    public Class checkProtobufClass() throws Throwable {
        return Class.forName("com.google.protobuf.Parser");
    }


    public ForestProtobufConverter getForestProtobufConverter() {
        if (forestProtobufConverter == null) {
            synchronized (this) {
                if (forestProtobufConverter == null) {
                    try {
                        checkProtobufClass();
                        Class clazz = Class.forName(PROTOBUF_CONVERTER_CLASS);
                        forestProtobufConverter = (ForestProtobufConverter) clazz.newInstance();
                    } catch (Throwable e) {
                        throw new ForestRuntimeException("forestProtobufConverter create exception", e);
                    }
                }
            }
        }
        return forestProtobufConverter;
    }
}