/*
 * Copyright (C) 2013 The Android Open Source Project
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

package android.support.v7.internal.view.menu;

import droidsafe.annotations.*;
import droidsafe.runtime.*;
import droidsafe.helpers.*;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.IBinder;
import android.support.v7.appcompat.R;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Helper for menus that appear as Dialogs (context and submenus).
 *
 * @hide
 */
public class MenuDialogHelper implements DialogInterface.OnKeyListener,
        DialogInterface.OnClickListener,
        DialogInterface.OnDismissListener,
        MenuPresenter.Callback {
@DSGeneratedField(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.156 -0400", hash_original_field = "E0FA7D9C40725579D8730D3DABE74643", hash_generated_field = "D444FB62DCB4C4F94C883BDD2D0F7012")

    private MenuBuilder mMenu;
@DSGeneratedField(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.158 -0400", hash_original_field = "E9678224249A33E904175F62AC8914F6", hash_generated_field = "F83602A20E99C2560EE913CF62B217D5")

    private AlertDialog mDialog;
@DSGeneratedField(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.160 -0400", hash_original_field = "DB66F87540FFC45043582E4DC1C87907", hash_generated_field = "DB66F87540FFC45043582E4DC1C87907")

    ListMenuPresenter mPresenter;
@DSGeneratedField(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.163 -0400", hash_original_field = "4249744317B9818C0707D11CFC7F603C", hash_generated_field = "66C54582B67241B61607C99E4C46472E")

    private MenuPresenter.Callback mPresenterCallback;

    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.166 -0400", hash_original_method = "E4FD56253F3F6ADA36D4CFC26E589D61", hash_generated_method = "A2D34494FC5FA1A02E25D6D0451BAFA9")
    
public MenuDialogHelper(MenuBuilder menu) {
        mMenu = menu;
    }

    /**
     * Shows menu as a dialog.
     *
     * @param windowToken Optional token to assign to the window.
     */
    @DSSafe(DSCat.SAFE_LIST)
    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.170 -0400", hash_original_method = "1E4FCC92B87A5FEFB76FC732CC250748", hash_generated_method = "637A89E54F27E80A66EB6C66D953970A")
    
public void show(IBinder windowToken) {
        // Many references to mMenu, create local reference
        final MenuBuilder menu = mMenu;

        // Get the builder for the dialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(menu.getContext());

        // Need to force Light Menu theme as list_menu_item_layout is usually against a dark bg and
        // AlertDialog's bg is white
        mPresenter = new ListMenuPresenter(R.layout.abc_list_menu_item_layout,
                R.style.Theme_AppCompat_CompactMenu_Dialog);

        mPresenter.setCallback(this);
        mMenu.addMenuPresenter(mPresenter);
        builder.setAdapter(mPresenter.getAdapter(), this);

        // Set the title
        final View headerView = menu.getHeaderView();
        if (headerView != null) {
            // Menu's client has given a custom header view, use it
            builder.setCustomTitle(headerView);
        } else {
            // Otherwise use the (text) title and icon
            builder.setIcon(menu.getHeaderIcon()).setTitle(menu.getHeaderTitle());
        }

        // Set the key listener
        builder.setOnKeyListener(this);

        // Show the menu
        mDialog = builder.create();
        mDialog.setOnDismissListener(this);

        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.type = WindowManager.LayoutParams.TYPE_APPLICATION_ATTACHED_DIALOG;
        if (windowToken != null) {
            lp.token = windowToken;
        }
        lp.flags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;

        mDialog.show();
    }

    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.173 -0400", hash_original_method = "7FA2E70C24A29492003E7D41632C85C5", hash_generated_method = "096BCE54A23C290B37C3E3056E9F3E91")
    
public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU || keyCode == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getRepeatCount() == 0) {
                Window win = mDialog.getWindow();
                if (win != null) {
                    View decor = win.getDecorView();
                    if (decor != null) {
                        KeyEvent.DispatcherState ds = decor.getKeyDispatcherState();
                        if (ds != null) {
                            ds.startTracking(event, this);
                            return true;
                        }
                    }
                }
            } else if (event.getAction() == KeyEvent.ACTION_UP && !event.isCanceled()) {
                Window win = mDialog.getWindow();
                if (win != null) {
                    View decor = win.getDecorView();
                    if (decor != null) {
                        KeyEvent.DispatcherState ds = decor.getKeyDispatcherState();
                        if (ds != null && ds.isTracking(event)) {
                            mMenu.close(true);
                            dialog.dismiss();
                            return true;
                        }
                    }
                }
            }
        }

        // Menu shortcut matching
        return mMenu.performShortcut(keyCode, event, 0);

    }

    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.175 -0400", hash_original_method = "9428581351DA56E5D84FF3309DF7BACF", hash_generated_method = "AB3FF55758B17C77DD7E4A23017466DF")
    
public void setPresenterCallback(MenuPresenter.Callback cb) {
        mPresenterCallback = cb;
    }

    /**
     * Dismisses the menu's dialog.
     *
     * @see Dialog#dismiss()
     */
    @DSSafe(DSCat.SAFE_LIST)
    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.177 -0400", hash_original_method = "85B0BC88009D26568BAEDC3852E0DEEF", hash_generated_method = "9414564B2F8D243C48DCAACFC2CBA6D0")
    
public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @DSSafe(DSCat.SAFE_LIST)
    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.179 -0400", hash_original_method = "3E9C803E65E5B82ED710485BB8BDB3C5", hash_generated_method = "A6B967A9965B0848EC018F0F8BD8D1EF")
    
@Override
    public void onDismiss(DialogInterface dialog) {
        mPresenter.onCloseMenu(mMenu, true);
    }

    @DSSafe(DSCat.SAFE_LIST)
    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.183 -0400", hash_original_method = "A718635F64E8AD72FBCA655033555352", hash_generated_method = "788789B7087ECE41FEFD46CE6E1231A5")
    
@Override
    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        if (allMenusAreClosing || menu == mMenu) {
            dismiss();
        }
        if (mPresenterCallback != null) {
            mPresenterCallback.onCloseMenu(menu, allMenusAreClosing);
        }
    }

    @DSSafe(DSCat.SAFE_LIST)
    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.186 -0400", hash_original_method = "DC034CF78D1777A9042EE11EB550F98E", hash_generated_method = "CD7A080BD9ED86827C1C30FFCB92B151")
    
@Override
    public boolean onOpenSubMenu(MenuBuilder subMenu) {
        if (mPresenterCallback != null) {
            return mPresenterCallback.onOpenSubMenu(subMenu);
        }
        return false;
    }

    @DSGenerator(tool_name = "Doppelganger", tool_version = "2.0", generated_on = "2014-09-18 21:46:39.188 -0400", hash_original_method = "21A50852386DBBCBCF36C27713EEFE3F", hash_generated_method = "EC7C2AD22E31451B41EEF76A8EC3930D")
    
public void onClick(DialogInterface dialog, int which) {
        mMenu.performItemAction((MenuItemImpl) mPresenter.getAdapter().getItem(which), 0);
    }
}
