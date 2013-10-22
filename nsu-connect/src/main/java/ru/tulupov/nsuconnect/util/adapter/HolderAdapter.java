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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class HolderAdapter<T, Holder> extends SimpleAdapter<T> {

    private final int layoutResourceId;

    public HolderAdapter(int layoutResourceId) {
        this.layoutResourceId = layoutResourceId;
    }

    protected abstract Holder initHolder(Context context, View view);

    protected abstract void updateHolder(Context context, Holder holder, T item, int position);

    @Override
    @SuppressWarnings("unchecked")
    protected View getView(Context context, int position, T item, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getLayoutResourceId(position), parent, false);
            holder = initHolder(context, convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
            if (holder == null) {
                holder = initHolder(context, convertView);
                convertView.setTag(holder);
            }
        }

        updateHolder(context, holder, item, position);

        return convertView;
    }

    protected int getLayoutResourceId(int position) {
        return layoutResourceId;
    }

}
