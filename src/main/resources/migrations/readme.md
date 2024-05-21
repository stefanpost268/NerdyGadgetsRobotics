# Database Migration Guide

## Introduction

This document provides a guide on how to perform database migrations for our application. Database migrations are managed with Flyway, a database migration tool that allows you to version control your database schema and apply changes in a consistent and repeatable manner.

## Prerequisites

Before you start the migration process, ensure you have the following:

- Access to the database system
- Necessary permissions to perform operations on the database
- Backup of all important data
- Flyway Desktop: https://www.red-gate.com/products/flyway/community/download/

## Migration Files

Migration files are located in the directory `WORK_DIR/src/main/resources/migrations`. Each file is a SQL script that makes some changes to the database schema.

The naming convention for migration files is as follows:
- `VERION_YYYYMMDDHHMMSS__description_of_migration.sql`

Where:
- `VERION_YYYYMMDDHHMMSS` is the version and the timestamp of when the migration was created. This is used to determine the order in which migrations should be run. Migrations are run from oldest to newest based on this timestamp.
- `description_of_migration` is a brief description of what the migration does.

For example, the file `V001_20240510120124__add_status_on_colum_order_table.sql` is a migration that was created on May 10, 2024 at 12:01:24 and it updates a table called `orders` and adds `status` column.

## Running Migrations
To run the migrations, you can use the Flyway desktop app to connect to your database and apply the migrations.

1. Open the Flyway Desktop app.
2. Click on the `+` button to add a new configuration.
3. Fill in the details for your database connection.
4. Click on the `Test Connection` button to ensure that the connection is successful.
5. Click on the `Save` button to save the configuration.
6. Click on the `Migrate` button to apply the migrations to the database.
7. You should see the output of the migration process in the console.
8. If there are any errors, you can check the logs for more information.
9. Once the migration is complete, you can verify that the changes have been applied to the database.

[//]: # (To run migrations, you would typically use a database migration tool that supports your database system. This tool would read the migration files in the `Migrations` directory and apply them in the order determined by their timestamps.)

[//]: # (You can also do it manually:)
[//]: # (- `mysql -u root nerdygadgets < './Database/Migrations/2023_11_27_1050_create_user_table.sql'`)