<%-- 
    Document   : inventory
    Created on : Oct 12, 2021, 9:19:04 PM
    Author     : 771684
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HOME nVentory: Inventory</title>
        <link type="text/css" rel="stylesheet" href="css/stylesheet.css">
    </head>
    <body>
        <h1>Home Inventory</h1>
        <h4>Menu</h4>
        <ul style="list-style:circle">
            <li><a href="inventory">Inventory</a></li> 
            <li><a href="admin">Admin</a></li>  
            <li><a href="login?logout">Logout</a></li>  
        </ul>  

        <h2>Home Inventory for ${username}</h2>
        <div>
            <td>
                <table>
                    <tr>
                        <th>Category</th>
                        <th>Item Name</th>
                        <th>Item Price</th>  
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    <c:forEach items="${items}" var="item">
                        <tr>
                            <td>${item.category.category_name}</td>
                            <td>${item.item_name}</td>
                            <td>${item.price}</td>
                            <td><a href="inventory?action=editItem&itemId=${item.item_id}">Edit</a><br></td>
                            <td><a href="inventory?action=deleteItem&itemId=${item.item_id}">Delete</a><br></td>

                        </tr>
                    </c:forEach>
                </table>
            </td>
        </div>

        <h2>Add Item</h2>
        <form method="POST">
            <label>Category:</label>
            <!--input for category-->
            <select name="category">                
                <option value="kitchen">Kitchen</option>
                <option value="bathroom">Bathroom</option>
                <option value="living room">Living Room</option>
                <option value="basement">Basement</option>
                <option value="bedroom">Bedroom</option>
                <option value="garage">Garage</option>
                <option value="office">Office</option>
                <option value="utility room">Utility Room</option>
                <option value="storage">Storage</option>
                <option value="other">Other</option>
            </select>
            <br>
            <label>Item Name:</label>
            <!--input for item-->
            <input type="text" name="item" value="${userItem}">
            <br>
            <label>Price:</label>
            <!--input for price-->
            <input type="text" name="price" value="${userPrice}"> 
            <br>
            <br>

            <input type="submit" value="Save"> 
            <input type="hidden" name="userAction" value="addItem">
            ${error}
        </form>
        
        <h2>Edit Item</h2>
        <form method="POST">
            <label>Category:</label>
            <!--input for category-->
            <select name="editCategory">   
                <option value="${editCategory}">${editCategory}</option>
                <option value="kitchen">Kitchen</option>
                <option value="bathroom">Bathroom</option>
                <option value="living room">Living Room</option>
                <option value="basement">Basement</option>
                <option value="bedroom">Bedroom</option>
                <option value="garage">Garage</option>
                <option value="office">Office</option>
                <option value="utility room">Utility Room</option>
                <option value="storage">Storage</option>
                <option value="other">Other</option>
            </select>
            <br>
            <label>Item Name:</label>
            <!--input for item-->
            <input type="text" name="editItem" value="${editItem}">
            <br>
            <label>Price:</label>
            <!--input for price-->
            <input type="text" name="editPrice" value="${editPrice}"> 
            <br>
            <br>

            <input type="submit" value="Save"> 
            <input type="hidden" name="userAction" value="editItem">
            ${editError}
        </form>

        <h2>Manage Account</h2>
        <div>
            <td>
                <table>
                    <tr>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Active</th>
                        <th>Edit</th>
                    </tr>
                    <tr>
                        <td>${user.email}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.activeStatus}</td>
                        <td><a href="inventory?action=editUser&email=${user.email}">Edit</a><br></td>

                    </tr>
                </table>
            </td>
        </div>
        <br>
        <form method="POST">
            <label>Email:</label>
            <input class="input" type="email" name="editEmail" placeholder="Email"  value="${editEmail}"><br>
            <label>Password:</label>
            <input  class="input"type="text" name="editPassword" placeholder="Password" value="${editPassword}"><br>            
            <label>First name:</label>
            <input class="input" type="text" name="editFirstName" placeholder="First Name" value="${editFirstName}"><br>
            <label>Last name:</label>
            <input class="input" type="text" name="editLastName" placeholder="Last Name" value="${editLastName}"><br>
            <a class="checkbox" style="">Active<input class="checkbox" type="checkbox" name="activeEdit"  style="padding: 1% 1% 1% 1%;"></a> <br>
            <br>
            <input type="submit" value="Save"> 
            <input type="hidden" name="userAction" value="saveUser">
        </form>
        <p>${message}</p>

    </body>
</html>
