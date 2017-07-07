/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipse.scout.widgets.client.ui.forms;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.IFormField;
import org.eclipse.scout.rt.client.ui.form.fields.bigdecimalfield.AbstractBigDecimalField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractRadioButton;
import org.eclipse.scout.rt.client.ui.form.fields.datefield.AbstractDateTimeField;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.imagefield.AbstractImageField;
import org.eclipse.scout.rt.client.ui.form.fields.longfield.AbstractLongField;
import org.eclipse.scout.rt.client.ui.form.fields.radiobuttongroup.AbstractRadioButtonGroup;
import org.eclipse.scout.rt.client.ui.form.fields.sequencebox.AbstractSequenceBox;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield2.AbstractSmartField2;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.AbstractSplitBox;
import org.eclipse.scout.rt.client.ui.form.fields.splitbox.ISplitBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.resource.BinaryResource;
import org.eclipse.scout.rt.platform.util.IOUtility;
import org.eclipse.scout.rt.platform.util.NumberUtility;
import org.eclipse.scout.rt.platform.util.ObjectUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.rt.shared.services.lookup.ILookupRow;
import org.eclipse.scout.rt.shared.services.lookup.LocalLookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.widgets.client.ui.forms.SequenceBoxForm.MainBox.ExamplesBox.FromToBox.DefaultBox.FromField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.PreviewBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.PreviewBox.PreviewField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.ModifiedField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.NameField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.DetailsBox.SizeField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.ExamplesBox.SplitVerticalField.SplitHorizontalField.FilesBox.FileTableField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.SplitVisibleEnabledField.SplitterPositionBox.MinSplitterPositionField;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.SplitVisibleEnabledField.SplitterVisibilityBox;
import org.eclipse.scout.widgets.client.ui.forms.SplitBoxForm.MainBox.SplitVisibleEnabledField.SplitterVisibilityBox.CollapsibleFieldBox;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFileTableField;

public class SplitBoxForm extends AbstractForm implements IPageForm {

  public SplitBoxForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("SplitBox");
  }

  @Override
  public void startPageForm() {
    startInternal(new PageFormHandler());
  }

  public DetailsBox getBottomBox() {
    return getFieldByClass(DetailsBox.class);
  }

  @Override
  public CloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  public DetailsBox getDetailsBox() {
    return getFieldByClass(DetailsBox.class);
  }

  public SplitterVisibilityBox getSplitterVisibilityBox() {
    return getFieldByClass(SplitterVisibilityBox.class);
  }

  public FileTableField getFileTableField() {
    return getFieldByClass(FileTableField.class);
  }

  public FilesBox getFilesBox() {
    return getFieldByClass(FilesBox.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public ModifiedField getModifiedField() {
    return getFieldByClass(ModifiedField.class);
  }

  public MinSplitterPositionField getMinSplitterPositionField() {
    return getFieldByClass(MinSplitterPositionField.class);
  }

  public CollapsibleFieldBox getCollapsibleFieldBox() {
    return getFieldByClass(CollapsibleFieldBox.class);
  }

  public NameField getNameField() {
    return getFieldByClass(NameField.class);
  }

  public PreviewField getPreviewField() {
    return getFieldByClass(PreviewField.class);
  }

  public PreviewBox getPreviewBox() {
    return getFieldByClass(PreviewBox.class);
  }

  public SizeField getSizeField() {
    return getFieldByClass(SizeField.class);
  }

  public ExamplesBox getExamplesBox() {
    return getFieldByClass(ExamplesBox.class);
  }

  public SplitHorizontalField getSplitHorizontalField() {
    return getFieldByClass(SplitHorizontalField.class);
  }

  public SplitVerticalField getSplitVerticalField() {
    return getFieldByClass(SplitVerticalField.class);
  }

  public FromField getfromField() {
    return getFieldByClass(FromField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Order(20)
    public class ExamplesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return TEXTS.get("Examples");
      }

      @Order(10)
      public class SplitVerticalField extends AbstractSplitBox {

        @Override
        protected Class<? extends IFormField> getConfiguredCollapsibleField() {
          return PreviewBox.class;
        }

        @Override
        protected String getConfiguredSplitterPositionType() {
          return SPLITTER_POSITION_TYPE_RELATIVE_SECOND;
        }

        @Order(10)
        public class SplitHorizontalField extends AbstractSplitBox {

          @Override
          protected boolean getConfiguredSplitHorizontal() {
            return false;
          }

          @Override
          protected double getConfiguredSplitterPosition() {
            return 0.6;
          }

          @Order(10)
          public class FilesBox extends AbstractGroupBox {

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Files");
            }

            @Order(10)
            public class FileTableField extends AbstractFileTableField {

              @Override
              protected int getConfiguredGridH() {
                return 5;
              }

              @Override
              protected boolean getConfiguredLabelVisible() {
                return false;
              }

              @Override
              protected void execInitField() {
                super.execInitField();

                for (IColumn c : getTable().getColumns()) {
                  if (c instanceof AbstractFileTableField.Table.DateModifiedColumn) {
                    c.setVisible(false);
                  }
                }
              }

              @Override
              protected void execResourceRowClick(BinaryResource resource) {
                reloadPreview(resource);
                reloadDetails(resource);
              }

              private void reloadPreview(BinaryResource resource) {
                if (isImage(resource)) {
                  getPreviewField().setImage(resource.getContent());
                }
                else {
                  getPreviewField().setImage(null);
                }
              }

              private boolean isImage(BinaryResource resource) {
                if (resource == null) {
                  return false;
                }
                String ext = IOUtility.getFileExtension(resource.getFilename()).toLowerCase();
                return ObjectUtility.isOneOf(ext, "jpg", "jpeg", "png");
              }

              private void reloadDetails(BinaryResource resource) {
                if (resource == null) {
                  getNameField().resetValue();
                  getSizeField().resetValue();
                  getModifiedField().resetValue();
                }
                else {
                  getNameField().setValue(resource.getFilename());
                  getSizeField().setValue(resource.getContent() == null ? 0L : resource.getContent().length);
                  getModifiedField().setValue(new Date(resource.getLastModified()));
                }
              }
            }
          }

          @Order(20)
          public class DetailsBox extends AbstractGroupBox {

            @Override
            protected int getConfiguredGridColumnCount() {
              return 1;
            }

            @Override
            protected String getConfiguredLabel() {
              return TEXTS.get("Details");
            }

            @Override
            protected void execInitField() {
              getNameField().setEnabled(false, true, true);
              getSizeField().setEnabled(false, true, true);
              getModifiedField().setEnabled(false, true, true);
            }

            @Order(10)
            public class NameField extends AbstractStringField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("Name");
              }
            }

            @Order(20)
            public class SizeField extends AbstractLongField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("SizeInBytes");
              }
            }

            @Order(40)
            public class ModifiedField extends AbstractDateTimeField {

              @Override
              protected String getConfiguredLabel() {
                return TEXTS.get("Modified");
              }
            }
          }
        }

        @Order(30)
        public class PreviewBox extends AbstractGroupBox {

          @Override
          protected String getConfiguredBackgroundColor() {
            return "D6D6D6";
          }

          @Override
          protected boolean getConfiguredBorderVisible() {
            return false;
          }

          @Override
          protected int getConfiguredHorizontalAlignment() {
            return 0;
          }

          @Order(10)
          public class PreviewField extends AbstractImageField {

            @Override
            protected boolean getConfiguredAutoFit() {
              return true;
            }

            @Override
            protected String getConfiguredBackgroundColor() {
              return "D6D6D6";
            }

            @Override
            protected int getConfiguredGridH() {
              return 8;
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }
          }
        }
      }
    }

    @Order(40)
    public class SplitVisibleEnabledField extends AbstractSplitBox {

      @Order(10)
      public class SplitterVisibilityBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected double getConfiguredGridWeightY() {
          return 0.0;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Splitter configuration";
        }

        @Order(10)
        public class VisiblePreviewField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("VisiblePreview");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return true;
          }

          @Override
          protected void execChangedValue() {
            getPreviewBox().setVisible(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getPreviewBox().isVisible());
          }
        }

        @Order(20)
        public class VisibleDetailsField extends AbstractBooleanField {

          @Override
          protected String getConfiguredLabel() {
            return TEXTS.get("VisibleDetails");
          }

          @Override
          protected boolean getConfiguredLabelVisible() {
            return true;
          }

          @Override
          protected void execChangedValue() {
            getDetailsBox().setVisible(getValue());
          }

          @Override
          protected void execInitField() {
            setValue(getDetailsBox().isVisible());
          }
        }

        @Order(30)
        public class EnabledSequenceBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return "Splitter enabled";
          }

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Order(10)
          public class EnabledVerticalField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Vertical";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getSplitVerticalField().setSplitterEnabled(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getSplitVerticalField().isSplitterEnabled());
            }
          }

          @Order(20)
          public class EnabledHorizontalField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Horizontal";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getSplitHorizontalField().setSplitterEnabled(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getSplitHorizontalField().isSplitterEnabled());
            }
          }
        }

        @Order(40)
        public class VerticalFieldConfigurationSequenceBox extends AbstractSequenceBox {

          @Override
          protected String getConfiguredLabel() {
            return "Field (V)";
          }

          @Override
          protected boolean getConfiguredAutoCheckFromTo() {
            return false;
          }

          @Order(10)
          public class MinimizedVerticalField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Minimized";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getSplitVerticalField().setFieldMinimized(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getSplitVerticalField().isFieldMinimized());

              getSplitVerticalField().addPropertyChangeListener(ISplitBox.PROP_FIELD_MINIMIZED, new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                  if (!isValueChanging()) {
                    setValueChangeTriggerEnabled(false);
                    try {
                      setValue((Boolean) evt.getNewValue());
                    }
                    finally {
                      setValueChangeTriggerEnabled(true);
                    }
                  }
                }
              });
            }
          }

          @Order(20)
          public class CollapsedVerticalField extends AbstractBooleanField {

            @Override
            protected String getConfiguredLabel() {
              return "Collapsed";
            }

            @Override
            protected boolean getConfiguredLabelVisible() {
              return false;
            }

            @Override
            protected void execChangedValue() {
              getSplitVerticalField().setFieldCollapsed(getValue());
            }

            @Override
            protected void execInitField() {
              setValue(getSplitVerticalField().isFieldCollapsed());

              getSplitVerticalField().addPropertyChangeListener(ISplitBox.PROP_FIELD_COLLAPSED, new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                  if (!isValueChanging()) {
                    setValueChangeTriggerEnabled(false);
                    try {
                      setValue((Boolean) evt.getNewValue());
                    }
                    finally {
                      setValueChangeTriggerEnabled(true);
                    }
                  }
                }
              });
            }
          }
        }

        @Order(50)
        @ClassId("28608587-a22c-4a7e-b7bf-38eb6e5a8566")
        public class CollapsibleFieldBox extends AbstractRadioButtonGroup<Class<? extends IFormField>> {
          @Override
          protected String getConfiguredLabel() {
            return "Collapsible field (V)";
          }

          @Override
          protected void execChangedValue() {
            if (getValue() != null) {
              getSplitVerticalField().setCollapsibleField(getForm().getFieldByClass(getValue()));
            }
            else {
              getSplitVerticalField().setCollapsibleField(null);
            }
          }

          @Override
          protected void execInitField() {
            setValue(getSplitVerticalField().getCollapsibleField().getClass());
          }

          @Order(1000)
          @ClassId("56d65119-9b5b-42ee-ab6c-0b2e854f1f43")
          public class FirstFieldButton extends AbstractRadioButton<Class<? extends IFormField>> {
            @Override
            protected Class<? extends IFormField> getConfiguredRadioValue() {
              return SplitHorizontalField.class;
            }

            @Override
            protected String getConfiguredLabel() {
              return "First";
            }
          }

          @Order(2000)
          @ClassId("c1ea9514-2730-40f1-88d6-23714baf05b0")
          public class SecondFieldButton extends AbstractRadioButton<Class<? extends IFormField>> {
            @Override
            protected Class<? extends IFormField> getConfiguredRadioValue() {
              return PreviewBox.class;
            }

            @Override
            protected String getConfiguredLabel() {
              return "Second";
            }
          }

          @Order(3000)
          @ClassId("5548008f-4c9a-49c3-a47e-61f6885813a8")
          public class NoneFieldButton extends AbstractRadioButton<Class> {
            @Override
            protected Class getConfiguredRadioValue() {
              return null;
            }

            @Override
            protected String getConfiguredLabel() {
              return "None";
            }
          }
        }
      }

      @Order(20)
      public class SplitterPositionBox extends AbstractGroupBox {

        @Override
        protected int getConfiguredGridColumnCount() {
          return 1;
        }

        @Override
        protected double getConfiguredGridWeightY() {
          return 0.0;
        }

        @Override
        protected String getConfiguredLabel() {
          return "Splitter position";
        }

        @Order(10)
        public class SplitterPositionTypeVField extends AbstractSmartField2<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Position type (V)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Splitter position type of vertical splitter";
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return P_SplitterPositionTypeLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(getSplitVerticalField().getSplitterPositionType());
            updateSplitterPositionVFieldBounds();
          }

          @Override
          protected String validateValueInternal(String rawValue) {
            if (rawValue == null) {
              return getInitValue();
            }
            return super.validateValueInternal(rawValue);
          }

          @Override
          protected void execChangedValue() {
            getSplitVerticalField().setSplitterPositionType(getValue());
            updateSplitterPositionVFieldBounds();
          }

          protected void updateSplitterPositionVFieldBounds() {
            if (ObjectUtility.isOneOf(getValue(), ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_FIRST, ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_SECOND)) {
              getFieldByClass(SplitterPositionVField.class).setMaxValue(BigDecimal.ONE);
              getFieldByClass(MinSplitterPositionField.class).setMaxValue(BigDecimal.ONE);
            }
            else {
              getFieldByClass(SplitterPositionVField.class).setMaxValue(null);
              getFieldByClass(MinSplitterPositionField.class).setMaxValue(null);
            }
          }
        }

        @Order(20)
        public class SplitterPositionVField extends P_AbstractSplitterPositionField {

          @Override
          protected String getConfiguredLabel() {
            return "Position (V)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Splitter position of vertical splitter";
          }

          @Override
          protected ISplitBox getSplitBox() {
            return getSplitVerticalField();
          }
        }

        @Order(25)
        @ClassId("3b873c6a-280b-4944-bfef-307455d34e4c")
        public class MinSplitterPositionField extends AbstractBigDecimalField {
          @Override
          protected String getConfiguredLabel() {
            return "Min Position (V)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Minimum splitter position of vertical splitter";
          }

          @Override
          protected BigDecimal getConfiguredMinValue() {
            return BigDecimal.ZERO;
          }

          @Override
          protected void execInitField() {
            setValue(NumberUtility.toBigDecimal(getSplitVerticalField().getMinSplitterPosition()));

            getSplitVerticalField().addPropertyChangeListener(ISplitBox.PROP_MIN_SPLITTER_POSITION, new PropertyChangeListener() {
              @Override
              public void propertyChange(PropertyChangeEvent evt) {
                if (!isValueChanging()) {
                  setValueChangeTriggerEnabled(false);
                  try {
                    setValue(NumberUtility.toBigDecimal((Double) evt.getNewValue()));
                  }
                  finally {
                    setValueChangeTriggerEnabled(true);
                  }
                }
              }
            });
          }

          @Override
          protected void execChangedValue() {
            getSplitVerticalField().setMinSplitterPosition(NumberUtility.toDouble(getValue()));
          }
        }

        @Order(30)
        public class SplitterPositionTypeHField extends AbstractSmartField2<String> {

          @Override
          protected String getConfiguredLabel() {
            return "Position type (H)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Splitter position type of horizontal splitter";
          }

          @Override
          protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
            return P_SplitterPositionTypeLookupCall.class;
          }

          @Override
          protected void execInitField() {
            setValue(getSplitHorizontalField().getSplitterPositionType());
            updateSplitterPositionHFieldBounds();
          }

          @Override
          protected void execChangedValue() {
            getSplitHorizontalField().setSplitterPositionType(getValue());
            updateSplitterPositionHFieldBounds();
          }

          protected void updateSplitterPositionHFieldBounds() {
            if (ObjectUtility.isOneOf(getValue(), ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_FIRST, ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_SECOND)) {
              getFieldByClass(SplitterPositionHField.class).setMaxValue(BigDecimal.ONE);
            }
            else {
              getFieldByClass(SplitterPositionHField.class).setMaxValue(null);
            }
          }

          @Override
          protected String validateValueInternal(String rawValue) {
            if (rawValue == null) {
              return getInitValue();
            }
            return super.validateValueInternal(rawValue);
          }
        }

        @Order(40)
        public class SplitterPositionHField extends P_AbstractSplitterPositionField {

          @Override
          protected String getConfiguredLabel() {
            return "Position (H)";
          }

          @Override
          protected String getConfiguredTooltipText() {
            return "Splitter position of horizontal splitter";
          }

          @Override
          protected ISplitBox getSplitBox() {
            return getSplitHorizontalField();
          }
        }
      }
    }

    @Order(50)
    public class CloseButton extends AbstractCloseButton {
    }
  }

  public class PageFormHandler extends AbstractFormHandler {
  }

  protected abstract class P_AbstractSplitterPositionField extends AbstractBigDecimalField {

    protected abstract ISplitBox getSplitBox();

    @Override
    protected BigDecimal getConfiguredMinValue() {
      return BigDecimal.ZERO;
    }

    @Override
    protected void execChangedValue() {
      if (getValue() != null) {
        getSplitBox().setSplitterPosition(getValue().doubleValue());
      }
    }

    @Override
    protected void execInitField() {
      // set initial value
      setValueWithoutValueChangeTriggers(getSplitBox().getSplitterPosition());
      // add listener
      getSplitBox().addPropertyChangeListener(ISplitBox.PROP_SPLITTER_POSITION, new PropertyChangeListener() {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
          if (!P_AbstractSplitterPositionField.this.isValueChanging()) {
            setValueWithoutValueChangeTriggers((double) evt.getNewValue());
          }
        }
      });
    }

    protected void setValueWithoutValueChangeTriggers(double value) {
      setValueChangeTriggerEnabled(false);
      try {
        setValue(BigDecimal.valueOf(value));
      }
      finally {
        setValueChangeTriggerEnabled(true);
      }
    }
  }

  public static class P_SplitterPositionTypeLookupCall extends LocalLookupCall<String> {
    private static final long serialVersionUID = 1L;

    @Override
    protected List<? extends ILookupRow<String>> execCreateLookupRows() {
      ArrayList<LookupRow<String>> rows = new ArrayList<LookupRow<String>>();
      rows.add(new LookupRow<String>(ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_FIRST, "Relative (first field)"));
      rows.add(new LookupRow<String>(ISplitBox.SPLITTER_POSITION_TYPE_RELATIVE_SECOND, "Relative (second field)"));
      rows.add(new LookupRow<String>(ISplitBox.SPLITTER_POSITION_TYPE_ABSOLUTE_FIRST, "Absolute (first field)"));
      rows.add(new LookupRow<String>(ISplitBox.SPLITTER_POSITION_TYPE_ABSOLUTE_SECOND, "Absolute (second field)"));
      return rows;
    }
  }
}
