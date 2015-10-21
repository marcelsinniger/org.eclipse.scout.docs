/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.contacts.event.client;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.contacts.event.client.event.EventOutline;
import org.eclipse.scout.rt.client.ui.desktop.AbstractDesktopExtension;
import org.eclipse.scout.rt.client.ui.desktop.outline.AbstractOutlineViewButton;
import org.eclipse.scout.rt.client.ui.desktop.outline.IOutline;

public class DesktopExtension extends AbstractDesktopExtension {

  public DesktopExtension() {
  }

  @Override
  protected List<Class<? extends IOutline>> getConfiguredOutlines() {
    List<Class<? extends IOutline>> outlines = new ArrayList<>();
    outlines.add(EventOutline.class);
    return outlines;
  }

  @Order(2000.0)
  public class EventManagementOutlineViewButton extends AbstractOutlineViewButton {

    public EventManagementOutlineViewButton() {
      super(getCoreDesktop(), EventOutline.class);
    }

    @Override
    protected String getConfiguredKeyStroke() {
      return "ctrl-shift-e";
    }
  }
}
