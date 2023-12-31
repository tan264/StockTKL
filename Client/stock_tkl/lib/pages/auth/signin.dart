import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/pages/auth/controller.dart';
import 'package:stock_tkl/pages/auth/signup.dart';

class SignInPage extends GetView<AuthController> {
  const SignInPage({super.key});

  @override
  Widget build(BuildContext context) {
    String username = "";
    String password = "";

    return Scaffold(
      resizeToAvoidBottomInset: false,
      backgroundColor: const Color.fromRGBO(27, 31, 35, 1),
      appBar: AppBar(
        backgroundColor: Colors.transparent,
        elevation: 0,
        leading: IconButton(
          icon: const Icon(
            Icons.arrow_back,
            color: Colors.white,
          ),
          onPressed: () {
            Navigator.of(context).pop();
          },
        ),
      ),
      body: SafeArea(
        child: Padding(
          padding: const EdgeInsets.all(16.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Center(
                child: Row(
                  mainAxisSize: MainAxisSize.min,
                  children: [
                    Image.asset('assets/images/logo.png',
                        width: 100, height: 100, fit: BoxFit.contain),
                    const SizedBox(width: 16),
                    const Text(
                      'Stock TKL',
                      style: TextStyle(
                        color: Color.fromRGBO(65, 148, 138, 1),
                        fontSize: 24,
                      ),
                    ),
                  ],
                ),
              ),
              const SizedBox(height: 20),
              const Text(
                'Welcome back',
                style: TextStyle(fontSize: 32, color: Colors.white),
              ),
              const SizedBox(height: 10),
              const Text(
                'Login to access your account below.',
                style: TextStyle(fontSize: 16, color: Colors.white70),
              ),
              const SizedBox(height: 30),
              TextField(
                onChanged: (value) {
                  username = value;
                },
                decoration: InputDecoration(
                  hintText: 'Enter your username...',
                  labelText: 'Username',
                  filled: true,
                  fillColor: Colors.white,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                  floatingLabelStyle: const TextStyle(
                      backgroundColor: Color.fromRGBO(27, 31, 35, 1),
                      fontSize: 20),
                ),
              ),
              const SizedBox(height: 16),
              TextField(
                onChanged: (value) {
                  password = value;
                },
                decoration: InputDecoration(
                  hintText: 'Enter your password...',
                  labelText: 'Password',
                  filled: true,
                  fillColor: Colors.white,
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(8),
                  ),
                  floatingLabelStyle: const TextStyle(
                      backgroundColor: Color.fromRGBO(27, 31, 35, 1),
                      fontSize: 20),
                ),
                obscureText: true,
              ),
              const SizedBox(height: 20),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  TextButton(
                    onPressed: () {
                      // Implement forgot password logic
                    },
                    child: const Text('Forgot Password?',
                        style: TextStyle(color: Colors.white70)),
                  ),
                  Obx(() {
                    if (controller.isLoading.value) {
                      return const CircularProgressIndicator();
                    } else {
                      return const SizedBox.shrink();
                    }
                  }),
                  ElevatedButton(
                    onPressed: () {
                      controller.login(username, password);
                    },
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Colors.teal,
                      foregroundColor: Colors.white,
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(30),
                      ),
                      padding: const EdgeInsets.symmetric(
                          horizontal: 28, vertical: 12),
                    ),
                    child: const Text('Sign In'),
                  )
                ],
              ),
              const SizedBox(height: 200),
              Expanded(
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Text('Don\'t have an account?',
                        style: TextStyle(color: Colors.white70)),
                    TextButton(
                      onPressed: () {
                        Navigator.of(context).push(MaterialPageRoute(
                            builder: (context) => const SignUpPage()));
                      },
                      child: const Text('Create',
                          style: TextStyle(color: Colors.teal)),
                    ),
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
