import 'package:flutter/material.dart';

class AccountPage extends StatelessWidget {
  const AccountPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    const String username = "Elon Musk";
    const String email = "elonmusk@example.com";

    return Scaffold(
      appBar: AppBar(
        title: const Text('Account'),
        backgroundColor: const Color.fromRGBO(27, 31, 35, 1),
      ),
      body: ListView(
        children: [
          UserAccountsDrawerHeader(
            accountName: const Text(username),
            accountEmail: const Text(email),
            currentAccountPicture: CircleAvatar(
              backgroundColor: Colors.grey.shade800,
              child: Text(
                username[0],
                style: const TextStyle(fontSize: 40.0),
              ),
            ),
            decoration: const BoxDecoration(color: Colors.teal),
          ),
          ListTile(
            leading: const Icon(Icons.person),
            title: const Text('Profile'),
            onTap: () {},
          ),
          ListTile(
            leading: const Icon(Icons.settings),
            title: const Text('Settings'),
            onTap: () {},
          ),
          ListTile(
            leading: const Icon(Icons.exit_to_app),
            title: const Text('Logout'),
            onTap: () {},
          ),
        ],
      ),
    );
  }
}
