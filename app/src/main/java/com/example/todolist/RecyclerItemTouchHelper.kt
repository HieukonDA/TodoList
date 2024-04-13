package com.example.todolist

import android.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.Adapter.ToDoAdapter


class RecyclerItemTouchHelper(private val adapter: ToDoAdapter) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT){
            val builder: AlertDialog.Builder = AlertDialog.Builder(adapter.getContext())
            builder.setTitle("Xóa nhiệm vụ")
            builder.setMessage("Bạn có muốn chắc xóa nhiệm vụ này")
            builder.setPositiveButton("Xác nhận") { dialog, which ->
                adapter.deleteItem(position)
            }
            builder.setNegativeButton(android.R.string.cancel) { dialog, which ->
                adapter.notifyItemChanged(position)
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
        else{
            adapter.editItem(position)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val icon : Drawable
        val background: ColorDrawable

        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset: Int = 20

        if (dX > 0){
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.edit)!!
            background = ColorDrawable(ContextCompat.getColor(adapter.getContext(), R.color.colorPrimaryDark))
        }else{
            icon = ContextCompat.getDrawable(adapter.getContext(), R.drawable.delete)!!
            background = ColorDrawable(Color.RED)
        }

        var iconMargin : Int = (itemView.height - icon.intrinsicHeight)/2
        var iconTop: Int = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        var iconBottom: Int = iconTop + icon.intrinsicHeight

        if (dX> 0){
            var iconLeft: Int = itemView.left + iconMargin
            var iconRight: Int = itemView.left + iconMargin + icon.intrinsicHeight
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom)
        }else if ( dX < 0){
            var iconLeft: Int = itemView.right - iconMargin - icon.intrinsicHeight
            var iconRight: Int = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background.setBounds(itemView.right + dX.toInt() + backgroundCornerOffset, itemView.top, itemView.right , itemView.bottom)
        }
        else{
            background.setBounds(0,0,0,0)
        }
        background.draw(c)
        icon.draw(c)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // Xử lý sự kiện di chuyển ở đây (nếu cần)
        return false // Trả về false nếu không xử lý sự kiện di chuyển
    }

}