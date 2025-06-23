![Uploading image.png…]()





# 🏢 Member Management System

A full-stack application with frontend, backend, and database, designed for learning CRUD operations.

## 📋 Features

- ✅ **Add Member** - Input username, password, and real name
- ✅ **View Members** - Display all member data in card format
- ✅ **Edit Member** - Modify username and real name
- ✅ **Delete Member** - Remove a specific member (with confirmation)
- ✅ **Search Members** - Search by username or name keyword
- ✅ **Responsive Design** - Supports mobile, tablet, and desktop
- ✅ **Real-time Feedback** - Instant operation result notifications

## 🛠️ Tech Stack
- **Frontend**: Native HTML + CSS + JavaScript
- **Backend**: Spring Boot + REST API (Java: 17, Maven: 3.9.9)
- **Database**: MySQL (via MAMP)
- **Communication**: AJAX + JSON

## 🚀 Getting Started

### 1. Start Backend Service
```bash
cd spring1-membercontrol7
mvn spring-boot:run
```

### 2. Access Frontend Interface
Open in browser:
```
http://localhost:8080
```

## 💻 Interface Overview

### Search Section
- Enter keyword in search box, click "🔍 Search" button
- Supports searching by username or real name
- Click "📋 Show All" to return full member list
- Supports Enter key for quick search

### Add/Edit Member Form
- **Add Mode**: Fill all fields, click "💾 Add Member"
- **Edit Mode**: Click "✏️ Edit" button on member card
- **Reset Form**: Click "🔄 Reset Form" to clear all fields

### Member List
- Displays member data in card format
- Each card shows: real name, username, ID
- Includes "✏️ Edit" and "🗑️ Delete" action buttons

## 🔧 API Endpoints

Frontend automatically calls the following backend APIs:

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/brad07/members` | Get all members |
| GET | `/brad07/member/{id}` | Get specific member |
| POST | `/brad07/member` | Add new member |
| PUT | `/brad07/member/{id}` | Update member |
| DELETE | `/brad07/member/{id}` | Delete member |
| GET | `/brad07/members/search/{keyword}` | Search members |

## 📱 Responsive Design

- **Desktop**: Full features, multi-column layout
- **Tablet**: Adjusted layout, maintains usability
- **Mobile**: Single-column layout, touch-friendly

## ⚠️ Notes

1. **Editing Member**: Leave password field empty to keep current password
2. **Deleting Member**: Requires confirmation, irreversible action
3. **Data Validation**: All fields are mandatory
4. **Network Errors**: Displays error message if backend connection fails

## 🎨 UI Design Features

- **Modern Design**: Gradient background and card-based layout
- **Visual Feedback**: Hover effects and animated transitions
- **Color Coding**:
  - 🟢 Green: Add/Success operations
  - 🔵 Blue: Edit/Info display
  - 🟠 Orange: Reset/Warning messages
  - 🔴 Red: Delete/Error messages

## 📁 File Structure

```
src/main/resources/static/
├── index.html      # Main HTML page
├── style.css       # CSS stylesheet
└── script.js       # JavaScript functionality
```

## 🔍 Troubleshooting

### 1. Cannot Load Member Data
- Ensure backend service is running
- Check database connection
- Inspect browser developer tools Console for errors

### 2. Adding Member Fails
- Check if username already exists
- Ensure all fields are filled
- Verify database `member` table exists

### 3. Search Function Not Responding
- Ensure search keyword is not empty
- Check if backend search API is working

## 📧 Technical Support

For issues, verify:
1. Spring Boot backend service is running
2. Database connection is properly configured
3. Browser developer tools for error messages
