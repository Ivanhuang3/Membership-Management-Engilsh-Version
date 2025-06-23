// API Base Path
const API_BASE = '/brad07';

// DOM Elements
const memberForm = document.getElementById('memberForm');
const membersList = document.getElementById('membersList');
const loadingMessage = document.getElementById('loadingMessage');
const messageBox = document.getElementById('messageBox');
const searchInput = document.getElementById('searchInput');

// Form Elements
const memberIdInput = document.getElementById('memberId');
const accountInput = document.getElementById('account');
const passwdInput = document.getElementById('passwd');
const realnameInput = document.getElementById('realname');
const submitBtn = document.getElementById('submitBtn');

// Current Edit Mode
let isEditMode = false;

// Initialize on Page Load
document.addEventListener('DOMContentLoaded', function() {
    loadAllMembers();
    
    // Form Submit Event
    memberForm.addEventListener('submit', handleFormSubmit);
    
    // Search Input Enter Key Event
    searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            searchMembers();
        }
    });
});

// Handle Form Submit
async function handleFormSubmit(e) {
    e.preventDefault();
    
    const memberData = {
        account: accountInput.value.trim(),
        passwd: passwdInput.value.trim(),
        realname: realnameInput.value.trim()
    };
    
    // Validate Form
    if (!memberData.account || !memberData.passwd || !memberData.realname) {
        showMessage('Please fill in all fields', 'error');
        return;
    }
    
    try {
        if (isEditMode) {
            await updateMember(memberIdInput.value, memberData);
        } else {
            await createMember(memberData);
        }
    } catch (error) {
        console.error('Operation failed:', error);
        showMessage('Operation failed: ' + error.message, 'error');
    }
}

// Create New Member
async function createMember(memberData) {
    try {
        const response = await fetch(`${API_BASE}/member`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(memberData)
        });
        
        const result = await response.json();
        
        if (result.error === 0) {
            showMessage('Member added successfully!', 'success');
            resetForm();
            loadAllMembers();
        } else {
            showMessage(result.mesg || 'Add failed', 'error');
        }
    } catch (error) {
        throw new Error('Network error');
    }
}

// Update Member
async function updateMember(id, memberData) {
    try {
        // Don't send password when updating
        const updateData = {
            account: memberData.account,
            realname: memberData.realname
        };
        
        const response = await fetch(`${API_BASE}/member/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(updateData)
        });
        
        const result = await response.json();
        
        if (result.error === 0) {
            showMessage('Member updated successfully!', 'success');
            resetForm();
            loadAllMembers();
        } else {
            showMessage(result.mesg || 'Update failed', 'error');
        }
    } catch (error) {
        throw new Error('Network error');
    }
}

// Load All Members
async function loadAllMembers() {
    showLoading(true);
    
    try {
        const response = await fetch(`${API_BASE}/members`);
        const result = await response.json();
        
        if (result.error === 0) {
            displayMembers(result.data || []);
        } else {
            showMessage('Failed to load member data: ' + result.mesg, 'error');
            displayMembers([]);
        }
    } catch (error) {
        console.error('Failed to load members:', error);
        showMessage('Failed to load member data', 'error');
        displayMembers([]);
    } finally {
        showLoading(false);
    }
}

// Search Members
async function searchMembers() {
    const keyword = searchInput.value.trim();
    
    if (!keyword) {
        showMessage('Please enter a search keyword', 'warning');
        return;
    }
    
    showLoading(true);
    
    try {
        const response = await fetch(`${API_BASE}/members/search/${encodeURIComponent(keyword)}`);
        const result = await response.json();
        
        if (result.error === 0) {
            displayMembers(result.data || []);
            showMessage(`Found ${result.data ? result.data.length : 0} results`, 'success');
        } else {
            showMessage('Search failed: ' + result.mesg, 'error');
            displayMembers([]);
        }
    } catch (error) {
        console.error('Search failed:', error);
        showMessage('Search failed', 'error');
        displayMembers([]);
    } finally {
        showLoading(false);
    }
}

// Display Member List
function displayMembers(members) {
    if (!members || members.length === 0) {
        membersList.innerHTML = '<div class="no-data">📋 No member data</div>';
        return;
    }
    
    const membersHTML = members.map(member => `
        <div class="member-card">
            <div class="member-info">
                <h3>👤 ${escapeHtml(member.realname)}</h3>
                <p><strong>Account:</strong> ${escapeHtml(member.account)}</p>
                <p><strong>ID:</strong> ${member.id}</p>
            </div>
            <div class="member-actions">
                <button class="edit-btn" onclick="editMember(${member.id})">
                    ✏️ Edit
                </button>
                <button class="delete-btn" onclick="deleteMember(${member.id}, '${escapeHtml(member.realname)}')">
                    🗑️ Delete
                </button>
            </div>
        </div>
    `).join('');
    
    membersList.innerHTML = membersHTML;
}

// Edit Member
async function editMember(id) {
    try {
        const response = await fetch(`${API_BASE}/member/${id}`);
        const result = await response.json();
        
        if (result.error === 0 && result.data) {
            const member = result.data;
            
            // Fill Form
            memberIdInput.value = member.id;
            accountInput.value = member.account;
            realnameInput.value = member.realname;
            passwdInput.value = ''; // Clear password field
            passwdInput.placeholder = 'Leave empty to keep current password';
            passwdInput.required = false; // Password not required when editing
            
            // Switch to Edit Mode
            isEditMode = true;
            submitBtn.textContent = '💾 Update Member';
            submitBtn.style.background = '#4299e1';
            
            // Scroll to Form
            document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
        } else {
            showMessage('Unable to load member data', 'error');
        }
    } catch (error) {
        console.error('Failed to load member data:', error);
        showMessage('Failed to load member data', 'error');
    }
}

// Delete Member
async function deleteMember(id, name) {
    if (!confirm(`Are you sure you want to delete member "${name}"? This action cannot be undone.`)) {
        return;
    }
    
    try {
        const response = await fetch(`${API_BASE}/member/${id}`, {
            method: 'DELETE'
        });
        
        const result = await response.json();
        
        if (result.error === 0) {
            showMessage('Member deleted successfully!', 'success');
            loadAllMembers();
        } else {
            showMessage(result.mesg || 'Delete failed', 'error');
        }
    } catch (error) {
        console.error('Failed to delete member:', error);
        showMessage('Failed to delete member', 'error');
    }
}

// Reset Form
function resetForm() {
    memberForm.reset();
    memberIdInput.value = '';
    passwdInput.placeholder = 'Enter password';
    passwdInput.required = true;
    
    // Return to Add Mode
    isEditMode = false;
    submitBtn.textContent = '💾 Add Member';
    submitBtn.style.background = '#48bb78';
}

// Show/Hide Loading Message
function showLoading(show) {
    loadingMessage.style.display = show ? 'block' : 'none';
    membersList.style.display = show ? 'none' : 'block';
}

// Show Message Notification
function showMessage(message, type = 'success') {
    messageBox.textContent = message;
    messageBox.className = `message-box ${type} show`;
    
    // Auto hide after 3 seconds
    setTimeout(() => {
        messageBox.className = 'message-box';
    }, 3000);
}

// HTML Escape Function
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Utility Function: Format Date
function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US');
} 