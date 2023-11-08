package com.example.homeworksix

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.example.homeworksix.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var users: MutableMap<String, List<String>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        users = mutableMapOf()
        binding.btnAddUser.setOnClickListener {
                if (!formIsValid()){
                    Toast.makeText(this, "Fill everything correctly!", Toast.LENGTH_SHORT).show()
                    binding.tvResult.text = ""
                }
                if(formIsValid() && userExists(binding.etEmail.text.toString())){
                    binding.tvResult.setTextColor(Color.RED)
                    binding.tvResult.text = getString(R.string.user_already_exists)

                }
                if(!userExists(binding.etEmail.text.toString()) && formIsValid()){
                   addUser(binding.etEmail, binding.etFirstName, binding.etLastName, binding.etAge)

                }

        }
        binding.btnRemoveUser.setOnClickListener {
            if(!formIsValid()){
                Toast.makeText(this, "Fill everything correctly!", Toast.LENGTH_SHORT).show()
                binding.tvResult.text = ""
            }
            if (formIsValid() &&!userExists(binding.etEmail.text.toString())){
                binding.tvResult.setTextColor(Color.RED)
                binding.tvResult.text = getString(R.string.user_does_not_exist)

            }

            if (formIsValid() && userExists(binding.etEmail.text.toString()) ){

                removeUser(binding.etEmail, binding.etFirstName, binding.etLastName, binding.etAge)
            }


            
        }
        binding.btnUpdateUser.setOnClickListener {
            if (!formIsValid()){
                Toast.makeText(this, "Fill everything correctly!", Toast.LENGTH_SHORT).show()
                binding.tvResult.text = ""
            }
            if (formIsValid() &&!userExists(binding.etEmail.text.toString())){
                binding.tvResult.setTextColor(Color.RED)
                binding.tvResult.text = getString(R.string.user_does_not_exist)

            }
            if(formIsValid() && userExists(binding.etEmail.text.toString())){
                updateUser(binding.etEmail, binding.etFirstName, binding.etLastName, binding.etAge)

            }

        }


    }
    private fun updateUser(email: AppCompatEditText, firstName: AppCompatEditText,
                           lastName: AppCompatEditText, age: AppCompatEditText){
        val textEmail = email.text.toString()
        val textFirstName = firstName.text.toString()
        val textLastName = lastName.text.toString()
        val textAge = age.text.toString()
        val info = users[textEmail]!!
        if ( textFirstName != info[0] || textLastName != info[1] ||
            textAge != info[2]){
            users[email.text.toString()] = listOf(firstName.text.toString(),
                lastName.text.toString(), age.text.toString())
            binding.tvResult.setTextColor(Color.GREEN)
            binding.tvResult.text = getString(R.string.changes_applied_successfully)
            email.setText("")
            firstName.setText("")
            lastName.setText("")
            age.setText("")

        }else{
            binding.tvResult.setTextColor(Color.RED)
            binding.tvResult.text = getString(R.string.you_have_to_make_changes)

        }

    }

    private fun addUser(email: AppCompatEditText, firstName: AppCompatEditText,
                        lastName: AppCompatEditText, age: AppCompatEditText){
        users[email.text.toString()] = listOf(firstName.text.toString(),
            lastName.text.toString(), age.text.toString())
        binding.tvResult.setTextColor(Color.GREEN)
        binding.tvResult.text = getString(R.string.user_added_successfully)
        email.setText("")
        firstName.setText("")
        lastName.setText("")
        age.setText("")
    }
    
    private fun removeUser(email: AppCompatEditText, firstName: AppCompatEditText,
                           lastName: AppCompatEditText, age: AppCompatEditText){
        val textEmail = email.text.toString()
        val textFirstName = firstName.text.toString()
        val textLastName = lastName.text.toString()
        val textAge = age.text.toString()
        val info = users[textEmail]!!
        if ( textFirstName == info[0] && textLastName == info[1] &&
            textAge == info[2]){
            users.remove(textEmail)
            binding.tvResult.setTextColor(Color.GREEN)
            binding.tvResult.text = getString(R.string.user_deleted_successfully)
            email.setText("")
            firstName.setText("")
            lastName.setText("")
            age.setText("")
        }else{
            binding.tvResult.setTextColor(Color.RED)
            binding.tvResult.text = getString(R.string.enter_credintals_correctly)
        }
        
        
    }

    private fun userExists(email: String): Boolean{
        val ko = email in users.keys
        return email in users.keys


    }

    private fun formIsValid(): Boolean{
        val pattern = Patterns.EMAIL_ADDRESS
        val validFirstName = (binding.etFirstName.text.toString().isNotEmpty() && binding.etFirstName.text.toString().matches(Regex("^[a-zA-Z]*$")))
        val validLastName = (binding.etLastName.text.toString().isNotEmpty() && binding.etLastName.text.toString().matches(Regex("^[a-zA-Z]*$")))
        val validAge = binding.etFirstName.text.toString().isNotEmpty()
        val validEmail =  pattern.matcher(binding.etEmail.text.toString()).matches()
//        Toast.makeText(this, "$validFirstName,", Toast.LENGTH_SHORT).show()
        return  validFirstName && validLastName && validAge && validEmail

    }
}