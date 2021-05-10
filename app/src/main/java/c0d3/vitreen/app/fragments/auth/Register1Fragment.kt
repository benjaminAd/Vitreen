package c0d3.vitreen.app.fragments.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import c0d3.vitreen.app.R
import c0d3.vitreen.app.utils.Constants.Companion.TAG
import c0d3.vitreen.app.utils.VFragment
import kotlinx.android.synthetic.main.fragment_register1.*

class Register1Fragment : VFragment(
    R.layout.fragment_register1,
    R.drawable.bigicon_authentification,
    -1
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // On register (part 2) button click, navigate to Register2 fragment
        buttonToRegister2.setOnClickListener {
            // Check if required inputs are filled
            if(isAnyRequiredInputEmpty(editTextEmail, editTextPassword, editTextPasswordConfirmation))
                return@setOnClickListener

            Log.i(TAG, "Register clicked")

            val email = inputToString(editTextEmail)
            val password = inputToString(editTextPassword)
            val passwordConfirmation = inputToString(editTextPasswordConfirmation)

            // Double check email and password after conversion
            if(email == null || password == null)
                return@setOnClickListener

            Log.i(TAG, "Double check succeeded")

            // Check if passwords are equals
            if(password != passwordConfirmation) {
                editTextPassword.editText?.text?.clear()
                editTextPasswordConfirmation.editText?.text?.clear()
                showMessage(R.string.passwords_not_equals)
                return@setOnClickListener
            }

            Log.i(TAG, "Passwords are the same")

            if(user == null) {
                Log.i(TAG, "User == null")
                viewModel.registerUser(email, password).observeOnce(viewLifecycleOwner, { errorCode ->
                    // If the call fails, show error message and hide loading spinner
                    if(handleError(errorCode)) return@observeOnce
                    // Else, navigate to Register2 fragment
                    navigateTo(R.id.action_navigation_register1_to_navigation_register2, "email" to email)
                })
            } else if(!isUserSignedIn()) {
                Log.i(TAG, "User anonymous")
                try {
                    viewModel.linkUser(user!!, email, password).observeOnce(viewLifecycleOwner, { errorCode ->
                        Log.i(TAG, "Observing with error " + if(errorCode != -1) getString(errorCode) else "-1")
                        // If the call fails, show error message and hide loading spinner
                        if(handleError(errorCode)) return@observeOnce
                        // Else, navigate to Register2 fragment
                        navigateTo(R.id.action_navigation_register1_to_navigation_register2, "email" to email)
                        Log.i(TAG, "Navigation -> register2")
                    })
                } catch (_: NullPointerException) {
                    showMessage()
                }
            } else {
                Log.i(TAG, "User signed in")
                navigateTo(R.id.action_navigation_register1_to_navigation_home)
            }
        }

        // On login button click, navigate to Login fragment
        buttonToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_register1_to_navigation_login)
        }

    }

}