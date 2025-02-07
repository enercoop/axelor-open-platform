/*
 * Axelor Business Solutions
 *
 * Copyright (C) 2005-2021 Axelor (<http://axelor.com>).
 *
 * This program is free software: you can redistribute it and/or  modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.axelor.data.xml;

import com.axelor.data.XStreamUtils;
import com.axelor.data.adapter.DataAdapter;
import com.google.common.collect.Lists;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import java.io.File;
import java.util.List;

@XStreamAlias("xml-inputs")
public class XMLConfig {

  @XStreamImplicit(itemFieldName = "adapter")
  private List<DataAdapter> adapters = Lists.newArrayList();

  @XStreamImplicit(itemFieldName = "input")
  private List<XMLInput> inputs = Lists.newArrayList();

  public List<DataAdapter> getAdapters() {
    if (adapters == null) {
      adapters = Lists.newArrayList();
    }
    return adapters;
  }

  public List<XMLInput> getInputs() {
    return inputs;
  }

  public static XMLConfig parse(File input) {
    XStream stream = XStreamUtils.createXStream();
    stream.setMode(XStream.NO_REFERENCES);
    stream.processAnnotations(XMLConfig.class);
    stream.registerConverter(
        new XMLBindConverter(
            stream.getConverterLookup().lookupConverterForType(XMLBind.class),
            stream.getReflectionProvider()));
    return (XMLConfig) stream.fromXML(input);
  }
}
