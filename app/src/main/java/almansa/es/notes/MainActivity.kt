package almansa.es.notes

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader


class MainActivity : AppCompatActivity() {

    private val FILENAME = "notesfile"

    private lateinit var listViewNotes : ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun save(view: View) {

        var sb = StringBuilder()

        sb.append(etNote.text.toString())
        sb.append(System.getProperty("line.separator"))

        this.openFileOutput(FILENAME, Context.MODE_APPEND).use {
            it.write(sb.toString().toByteArray())
            it.close()
        }

        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()

    }

    fun load(view: View) {

        val fileStream = openFileInput(FILENAME)
        val inputStreamReader = InputStreamReader(fileStream)
        val bufferedReader = BufferedReader(inputStreamReader)

        var lines = bufferedReader.readLines()

        if(lines.isEmpty())
            lines = arrayListOf("Empty List")

        listViewNotes = findViewById(R.id.lvNotes)

        listViewNotes.adapter = NoteAdapter(this, lines as ArrayList)
    }

    fun clear(view: View){
        listViewNotes.adapter = NoteAdapter(this, ArrayList())

        this.openFileOutput(FILENAME, Context.MODE_PRIVATE).use {
            it.flush()
        }

    }

}
