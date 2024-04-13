package com.example.todolist

import android.content.DialogInterface

public interface DialogCloseListener {
    public fun handleDialogClose(dialog : DialogInterface)
}