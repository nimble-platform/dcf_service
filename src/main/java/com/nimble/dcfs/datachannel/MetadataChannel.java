/*
 * Copyright 2018 a.musumeci.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nimble.dcfs.datachannel;

import java.util.ArrayList;

/**
 *
 * @author a.musumeci
 */
public class MetadataChannel {
    String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ArrayList<MetadataRow> getStreamMetadata() {
        return streamMetadata;
    }

    public void setStreamMetadata(ArrayList<MetadataRow> streamMetadata) {
        this.streamMetadata = streamMetadata;
    }
    private ArrayList<MetadataRow> streamMetadata = new ArrayList();
            
}
