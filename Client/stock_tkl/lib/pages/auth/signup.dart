import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:stock_tkl/pages/auth/controller.dart';

class SignUpPage extends GetView<AuthController> {
  const SignUpPage({super.key});

  @override
  Widget build(BuildContext context) {
    String username = "";
    String password = "";
    String email = "";
    String confirmPassword = "";
    String fullName = "";

    return Scaffold(
      // resizeToAvoidBottomInset: false,
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
      body: SingleChildScrollView(
        child: SafeArea(
          child: Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.start,
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
                  'Get Started',
                  style: TextStyle(fontSize: 32, color: Colors.white),
                ),
                const SizedBox(height: 10),
                const Text(
                  'Create your account below.',
                  style: TextStyle(fontSize: 16, color: Colors.white70),
                ),
                const SizedBox(height: 35),
                TextField(
                  onChanged: (value) {
                    email = value;
                  },
                  decoration: InputDecoration(
                    hintText: 'Enter your email...',
                    labelText: 'Email Address',
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
                    fullName = value;
                  },
                  decoration: InputDecoration(
                    hintText: 'Enter your fullname...',
                    labelText: 'Fullname',
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
                TextField(
                  onChanged: (value) {
                    confirmPassword = value;
                  },
                  decoration: InputDecoration(
                    hintText: 'Enter your password...',
                    labelText: 'Confirm Password',
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
                const SizedBox(height: 30),
                Row(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: [
                    Obx(() {
                      if (controller.isLoading.value) {
                        return const CircularProgressIndicator();
                      } else {
                        return const SizedBox.shrink();
                      }
                    }),
                    ElevatedButton(
                      onPressed: () {
                        // account logic
                        controller.register(username, password, fullName,
                            confirmPassword, email);
                      },
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.teal,
                        foregroundColor: Colors.white,
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(30.0),
                        ),
                        padding: const EdgeInsets.symmetric(
                            horizontal: 30, vertical: 14),
                      ),
                      child: const Text(
                        'Create Account',
                        style: TextStyle(fontSize: 16),
                      ),
                    ),
                    const SizedBox(height: 10),
                  ],
                )
              ],
            ),
          ),
        ),
      ),
    );
  }
}
