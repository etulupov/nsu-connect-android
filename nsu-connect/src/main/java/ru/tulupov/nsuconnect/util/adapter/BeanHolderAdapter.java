/*
 * Copyright (c) 2013 Noveo Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Except as contained in this notice, the name(s) of the above copyright holders
 * shall not be used in advertising or otherwise to promote the sale, use or
 * other dealings in this Software without prior written authorization.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package ru.tulupov.nsuconnect.util.adapter;

import android.content.Context;
import android.view.View;

import com.noveo.android.bean.Bean;
import com.noveo.android.bean.BeanRegistry;
import com.noveo.android.bean.Property;

import java.lang.reflect.InvocationTargetException;

public abstract class BeanHolderAdapter<T, Holder> extends HolderAdapter<T, Holder> {


    private final Bean<Holder> holderBean;

    public BeanHolderAdapter(int layoutResourceId, Class<Holder> holderClass) {
        super(layoutResourceId);
        this.holderBean = BeanRegistry.getInstance().get(holderClass);
    }

    @Override
    protected Holder initHolder(Context context, View view) {
        try {
            Holder holder = holderBean.newBean();

            for (Property<Holder> property : holderBean.getProperties()) {
                FindViewById annotation = property.getAnnotation(FindViewById.class);
                if (annotation != null) {
                    View v = view.findViewById(annotation.value());
                    Class<?> type = property.getType();
                    property.setValue(holder, type.cast(v));
                }
            }

            return holder;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
