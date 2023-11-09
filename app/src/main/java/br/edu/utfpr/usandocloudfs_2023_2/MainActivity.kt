package br.edu.utfpr.usandocloudfs_2023_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.edu.utfpr.usandocloudfs_2023_2.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView( binding.root )

        binding.btIncluir.setOnClickListener {
            btIncluirOnClick();
        }

        binding.btAlterar.setOnClickListener {
            btAlterarOnClick();
        }

        binding.btExcluir.setOnClickListener {
            btExcluirOnClick();
        }

        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick();
        }

        binding.btListar.setOnClickListener {
            btListarOnClick();
        }
    }

    private fun btListarOnClick() {
        var saida = StringBuilder()

        db.collection("Pessoa")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    saida.append( "${document.data.get("nome")} - ${document.data.get("telefone")} \n")
                }
                Toast.makeText( this, saida, Toast.LENGTH_LONG ).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText( this, "Erro na Listagem", Toast.LENGTH_LONG ).show()
                Log.w( "Erro", "Error getting documents.", exception)

            }
    }

    private fun btPesquisarOnClick() {
        var saida = StringBuilder()

        db.collection("Pessoa")
            .whereEqualTo( "nome", binding.etNome.text.toString() )
            .get()
            .addOnSuccessListener { result ->
                if ( result.isEmpty ) {
                    saida.append("Registro não encontrado")
                } else {
                    val registro = result.elementAt( 0 ).data
                    saida.append( "${registro.get("nome")} - ${registro.get("telefone")} \n")
                }
                Toast.makeText( this, saida, Toast.LENGTH_LONG ).show()
            }
            .addOnFailureListener { exception ->
                Toast.makeText( this, "Erro na Pesquisa", Toast.LENGTH_LONG ).show()
                Log.w( "Erro", "Error getting documents.", exception)

            }


    }

    private fun btExcluirOnClick() {

// Add a new document with a generated ID
        db.collection("Pessoa")
            .document( binding.etNome.text.toString() )
            .delete()
            .addOnSuccessListener { documentReference ->
                Toast.makeText( this, "Registro Excluído com Sucesso", Toast.LENGTH_LONG ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText( this, "Erro na Exclusão", Toast.LENGTH_LONG ).show()
                Log.w( "Erro", "Error adding document", e)
            }

    }

    private fun btAlterarOnClick() {
        val registro = hashMapOf(
            "nome" to binding.etNome.text.toString(),
            "telefone" to binding.etTelefone.text.toString()
        )

// Add a new document with a generated ID
        db.collection("Pessoa")
            .document( binding.etNome.text.toString() )
            .set(registro)
            .addOnSuccessListener { documentReference ->
                Toast.makeText( this, "Registro Alterado com Sucesso", Toast.LENGTH_LONG ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText( this, "Erro na Alteração", Toast.LENGTH_LONG ).show()
                Log.w( "Erro", "Error adding document", e)
            }

    }

    private fun btIncluirOnClick() {

        val registro = hashMapOf(
            "nome" to binding.etNome.text.toString(),
            "telefone" to binding.etTelefone.text.toString()
        )

// Add a new document with a generated ID
        db.collection("Pessoa")
            .document(binding.etNome.text.toString() )
            .set(registro)
            .addOnSuccessListener { documentReference ->
                Toast.makeText( this, "Registro Incluído com Sucesso", Toast.LENGTH_LONG ).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText( this, "Erro na Inclusão", Toast.LENGTH_LONG ).show()
                Log.w( "Erro", "Error adding document", e)
            }
    }
}