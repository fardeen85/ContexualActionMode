package com.example.pcontexualactionmod

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  lateinit var po : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitle("MyActionModeApp")
        toolbar.setTitleTextColor(getResources().getColor(R.color.actionmode))

        val data = ArrayList<String>()
        data.add("Item1")
        data.add("item2")
        data.add("item3")
        data.add("item4")
        var adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data)
        var madapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_2,data)
        mylistview.adapter = adapter
        mylistview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL)

      var mv =  mylistview.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener{
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when(item!!.itemId){

                    R.id.delete ->{


                        adapter.remove(po.toString())
                        data.remove(po.toString())
                        madapter.remove(po.toString())
                        madapter.notifyDataSetChanged()
                        adapter.notifyDataSetChanged()
                        return true
                    }

                    R.id.deleteall ->{

                        var a = AlertDialog.Builder(this@MainActivity)
                        a.setTitle("Confirm delete")
                        a.setMessage("Are yu sure you want to delete all items")
                        a.setPositiveButton("Yes",{DialogInterface,i->

                            adapter.clear()
                            adapter.notifyDataSetChanged()
                            madapter.clear()
                            adapter.notifyDataSetChanged()

                        })
                        a.setNegativeButton("No",{DialogInterface,i ->

                            a.setCancelable(true)
                        })
                        a.show()


                    }

                }

                return true
            }

            override fun onItemCheckedStateChanged(
                mode: ActionMode?,
                position: Int,
                id: Long,
                checked: Boolean
            ) {
                 po = mylistview.getItemAtPosition(position).toString()
                var count = mylistview.getCheckedItemCount().toString()
                 mode!!.setTitle("$count items selected")

            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {

                menuInflater.inflate(R.menu.listmenu,menu)
                mylistview.adapter = madapter
                toolbar.setBackgroundColor(getResources().getColor(R.color.actionmode))
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {

                toolbar.setBackgroundColor(getResources().getColor(R.color.toolbar))

            }




        })



    }




}
