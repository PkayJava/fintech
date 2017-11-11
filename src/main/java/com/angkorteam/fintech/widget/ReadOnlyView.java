package com.angkorteam.fintech.widget;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.angkorteam.fintech.Application;
import com.angkorteam.framework.wicket.markup.html.form.select2.Option;

public class ReadOnlyView extends Label {

    private String format;

    public ReadOnlyView(String id, IModel<?> value) {
        super(id, value);
    }

    public ReadOnlyView(String id) {
        this(id, "");
    }

    public ReadOnlyView(String id, String value) {
        super(id, value);
    }

    public ReadOnlyView(String id, String[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Boolean value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Boolean[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Byte value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Byte value[]) {
        super(id, value);
    }

    public ReadOnlyView(String id, Byte value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Byte[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, byte value) {
        super(id, value);
    }

    public ReadOnlyView(String id, byte[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, byte value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, byte[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Short value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Short value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, short value) {
        super(id, value);
    }

    public ReadOnlyView(String id, short value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Short[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Short[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, short[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, short[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Integer value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Integer value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, int value) {
        super(id, value);
    }

    public ReadOnlyView(String id, int value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Integer[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Integer[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, int[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, int[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Long value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Long value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, long value) {
        super(id, value);
    }

    public ReadOnlyView(String id, long value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Long[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Long[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, long[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, long[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Float value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Float value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, float value) {
        super(id, value);
    }

    public ReadOnlyView(String id, float value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Float[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Float[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, float[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, float[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Double value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Double value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, double value) {
        super(id, value);
    }

    public ReadOnlyView(String id, double value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Double[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Double[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, double[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, double[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigInteger value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigInteger value) {
        super(id, value);
    }

    public ReadOnlyView(String id, BigInteger value[], String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigInteger value[]) {
        super(id, value);
    }

    public ReadOnlyView(String id, BigDecimal value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigDecimal value) {
        super(id, value);
    }

    public ReadOnlyView(String id, BigDecimal value[], String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, BigDecimal value[]) {
        super(id, value);
    }

    public ReadOnlyView(String id, Option value) {
        super(id, value);
    }

    public ReadOnlyView(String id, char value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Character value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Date value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, Option[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, char[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Character[] value) {
        super(id, value);
    }

    public ReadOnlyView(String id, Date[] value, String format) {
        super(id, value);
        this.format = format;
    }

    public ReadOnlyView(String id, List<?> value) {
        super(id, Model.ofList(value));
    }

    public ReadOnlyView(String id, List<?> value, String format) {
        super(id, Model.ofList(value));
        this.format = format;
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        tag.put("readonly", "readonly");
    }

    @Override
    public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
        IModel<?> model = getDefaultModel();
        if (model == null) {
            replaceComponentTagBody(markupStream, openTag, "");
        } else {
            Object object = model.getObject();
            if (object == null) {
                replaceComponentTagBody(markupStream, openTag, "");
            } else {
                List<String> objects = new ArrayList<>();
                Class<?> objectClass = object.getClass();
                if (objectClass == List.class) {
                    for (Object v : (List<Object>) object) {
                        if (v != null) {
                            Class<?> vClass = v.getClass();
                            if (vClass == Boolean.class || vClass == boolean.class) {
                                objects.add((boolean) object ? "Yes" : "No");
                            } else if (vClass == Byte.class || vClass == byte.class) {
                                objects.add(this.format == null || "".equals(this.format) ? String.valueOf((byte) object) : Application.FORMATS.get(this.format).format((byte) object));
                            } else if (vClass == Short.class || vClass == short.class) {
                                objects.add(this.format == null || "".equals(this.format) ? String.valueOf((short) object) : Application.FORMATS.get(this.format).format((short) object));
                            } else if (vClass == Integer.class || vClass == int.class) {
                                objects.add(this.format == null || "".equals(this.format) ? String.valueOf((int) object) : Application.FORMATS.get(this.format).format((int) object));
                            } else if (vClass == Long.class || vClass == long.class) {
                                objects.add(this.format == null || "".equals(this.format) ? String.valueOf((long) object) : Application.FORMATS.get(this.format).format((long) object));
                            } else if (vClass == Float.class || vClass == float.class) {
                                objects.add(this.format == null || "".equals(this.format) ? String.valueOf((float) object) : Application.FORMATS.get(this.format).format((float) object));
                            } else if (vClass == Double.class || vClass == double.class) {
                                objects.add(this.format == null || "".equals(this.format) ? String.valueOf((double) object) : Application.FORMATS.get(this.format).format((double) object));
                            } else if (vClass == Character.class || vClass == char.class) {
                                objects.add(String.valueOf((Character) object));
                            } else if (vClass == BigInteger.class) {
                                objects.add(this.format == null || "".equals(this.format) ? String.valueOf(((BigInteger) object).longValue()) : Application.FORMATS.get(this.format).format(((BigInteger) object).longValue()));
                            } else if (vClass == BigDecimal.class) {
                                objects.add(this.format == null || "".equals(this.format) ? String.valueOf(((BigDecimal) object).doubleValue()) : Application.FORMATS.get(this.format).format(((BigDecimal) object).doubleValue()));
                            } else if (vClass == Date.class) {
                                objects.add(DateFormatUtils.format((Date) object, this.format));
                            } else if (vClass == Option.class) {
                                objects.add(((Option) object).getText());
                            } else if (vClass == String.class) {
                                if (!"".equals(object)) {
                                    objects.add((String) object);
                                }
                            }
                        }
                    }
                } else if (objectClass == Boolean[].class) {
                    for (Boolean v : (Boolean[]) object) {
                        if (v != null) {
                            objects.add(v ? "Yes" : "No");
                        }
                    }
                } else if (objectClass == boolean[].class) {
                    for (boolean v : (boolean[]) object) {
                        objects.add(v ? "Yes" : "No");
                    }
                } else if (objectClass == Byte[].class) {
                    for (Byte v : (Byte[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                        }
                    }
                } else if (objectClass == byte[].class) {
                    for (byte v : (byte[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                    }
                } else if (objectClass == Short[].class) {
                    for (Short v : (Short[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                        }
                    }
                } else if (objectClass == short[].class) {
                    for (short v : (short[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                    }
                } else if (objectClass == Integer[].class) {
                    for (Integer v : (Integer[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                        }
                    }
                } else if (objectClass == int[].class) {
                    for (int v : (int[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                    }
                } else if (objectClass == Long[].class) {
                    for (Long v : (Long[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                        }
                    }
                } else if (objectClass == long[].class) {
                    for (long v : (long[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                    }
                } else if (objectClass == Float[].class) {
                    for (Float v : (Float[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                        }
                    }
                } else if (objectClass == float[].class) {
                    for (float v : (float[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                    }
                } else if (objectClass == Double[].class) {
                    for (Double v : (Double[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                        }
                    }
                } else if (objectClass == double[].class) {
                    for (double v : (double[]) object) {
                        objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v));
                    }
                } else if (objectClass == BigInteger[].class) {
                    for (BigInteger v : (BigInteger[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v.longValue()));
                        }
                    }
                } else if (objectClass == BigDecimal[].class) {
                    for (BigDecimal v : (BigDecimal[]) object) {
                        if (v != null) {
                            objects.add(this.format == null || "".equals(this.format) ? String.valueOf(v) : Application.FORMATS.get(this.format).format(v.doubleValue()));
                        }
                    }
                } else if (objectClass == Date[].class) {
                    for (Date v : (Date[]) object) {
                        if (v != null) {
                            objects.add(DateFormatUtils.format(v, this.format));
                        }
                    }
                } else if (objectClass == Character[].class) {
                    for (Character v : (Character[]) object) {
                        if (v != null) {
                            objects.add(String.valueOf(v));
                        }
                    }
                } else if (objectClass == char[].class) {
                    for (char v : (char[]) object) {
                        objects.add(String.valueOf(v));
                    }
                } else if (objectClass == String[].class) {
                    for (String v : (String[]) object) {
                        if (v != null && !"".equals(v)) {
                            objects.add(v);
                        }
                    }
                } else if (objectClass == Option[].class) {
                    for (Option v : (Option[]) object) {
                        if (v != null) {
                            objects.add(v.getText());
                        }
                    }
                } else if (objectClass == Boolean.class || objectClass == boolean.class) {
                    objects.add((boolean) object ? "Yes" : "No");
                } else if (objectClass == Byte.class || objectClass == byte.class) {
                    objects.add(this.format == null || "".equals(this.format) ? String.valueOf((byte) object) : Application.FORMATS.get(this.format).format((byte) object));
                } else if (objectClass == Short.class || objectClass == short.class) {
                    objects.add(this.format == null || "".equals(this.format) ? String.valueOf((short) object) : Application.FORMATS.get(this.format).format((short) object));
                } else if (objectClass == Integer.class || objectClass == int.class) {
                    objects.add(this.format == null || "".equals(this.format) ? String.valueOf((int) object) : Application.FORMATS.get(this.format).format((int) object));
                } else if (objectClass == Long.class || objectClass == long.class) {
                    objects.add(this.format == null || "".equals(this.format) ? String.valueOf((long) object) : Application.FORMATS.get(this.format).format((long) object));
                } else if (objectClass == Float.class || objectClass == float.class) {
                    objects.add(this.format == null || "".equals(this.format) ? String.valueOf((float) object) : Application.FORMATS.get(this.format).format((float) object));
                } else if (objectClass == Double.class || objectClass == double.class) {
                    objects.add(this.format == null || "".equals(this.format) ? String.valueOf((double) object) : Application.FORMATS.get(this.format).format((double) object));
                } else if (objectClass == Character.class || objectClass == char.class) {
                    objects.add(String.valueOf((Character) object));
                } else if (objectClass == BigInteger.class) {
                    objects.add(this.format == null || "".equals(this.format) ? String.valueOf(((BigInteger) object).longValue()) : Application.FORMATS.get(this.format).format(((BigInteger) object).longValue()));
                } else if (objectClass == BigDecimal.class) {
                    objects.add(this.format == null || "".equals(this.format) ? String.valueOf(((BigDecimal) object).doubleValue()) : Application.FORMATS.get(this.format).format(((BigDecimal) object).doubleValue()));
                } else if (objectClass == Date.class) {
                    objects.add(DateFormatUtils.format((Date) object, this.format));
                } else if (objectClass == Option.class) {
                    objects.add(((Option) object).getText());
                } else if (objectClass == String.class) {
                    if (!"".equals(object)) {
                        objects.add((String) object);
                    }
                }
                replaceComponentTagBody(markupStream, openTag, StringUtils.join(objects, ", "));
            }
        }
    }

}
