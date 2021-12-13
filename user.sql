USE [chat_app]
GO
/****** Object:  Table [dbo].[users]    Script Date: 10/11/2021 21:53:41 ******/
SET IDENTITY_INSERT [dbo].[users] ON
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (2, N'https://s120-ava-talk.zadn.vn/f/9/1/8/2/120/e87b0cf6f1fdb40730753033cc665a3b.jpg', 0, N'x@gmail.com', N'a', 1, N'a', N'123456', N'0522977010', N'true', N'anhhao')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (3, N'https://s120-ava-talk.zadn.vn/4/a/d/1/4/120/fe7417003801cc3c86bac06d381c6ea4.jpg', 0, N'y@gmail.com', N'b', 0, N'b', N'123456', N'0918608327', N'true', N'hao1')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (5, NULL, 1, N'z@gmailcom', N'c', 1, N'c', N'123456', N'01678175053', N'true', N'hao2')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (6, NULL, 0, N'dat@gmail.com', N'tien', 1, N'dat', N'123456', N'0911531911', N'true', N'tiendat')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (7, NULL, 0, N'dat1@gmail.com', N'd', 1, N'e', N'123456', N'0911531922', N'true', N'dat1')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (9, NULL, 0, N'dat2@gmail.com', N'dat', 0, N'dat', N'123456', N'0911531933', N'true', N'dat2')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (10, NULL, 0, N'dat3@gmail.com', N'dat', 0, N'dat', N'123456', N'0911531944', N'true', N'dat3')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (12, NULL, 0, N'dat4@gmail.com', N'dat', 0, N'dat', N'123456', N'0911531955', N'true', N'dat4')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (13, NULL, 0, N'a@gmail.com', N'nhi', 0, N'nhi', N'123456', N'0911531966', N'true', N'nhi')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (14, NULL, 0, N'b@gmail.com', N'nhi2', 0, N'nhi2', N'123456', N'0911531977', N'true', N'nhi2')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (15, NULL, 0, N'c@gmail.com', N'nhi3', 0, N'nhi3', N'123456', N'0911531988', N'true', N'nhi3')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (16, NULL, 0, N'd@gmail.com', N'nhi4', 0, N'nhi4', N'123456', N'0911531999', N'true', N'nhi4')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (17, NULL, 0, N'aa@gmail.com', N'dat1', 0, N'dat1', N'123456', N'0911531912', N'true', N'dat11')
INSERT [dbo].[users] ([userId], [avatar], [disable], [email], [firstName], [gender], [lastName], [password], [phoneNumber], [status], [userName]) VALUES (18, NULL, 0, N'bb@gmail.com', N'ttd', 0, N'ttd', N'123456', N'0911531913', N'true', N'ttd')
SET IDENTITY_INSERT [dbo].[users] OFF
/****** Object:  Table [dbo].[login_history]    Script Date: 10/11/2021 21:53:41 ******/
/****** Object:  Table [dbo].[friend]    Script Date: 10/11/2021 21:53:41 ******/
SET IDENTITY_INSERT [dbo].[friend] ON
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (1, 0, NULL, 1, 2, 3)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (2, 0, NULL, 1, 7, 6)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (3, 0, NULL, 1, 3, 6)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (4, 0, NULL, 0, 9, 6)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (5, 0, NULL, 0, 2, 6)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (6, 1, NULL, 1, 10, 6)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (7, 1, NULL, 1, 12, 6)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (8, 0, NULL, 1, 6, 13)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (9, 0, NULL, 1, 6, 14)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (12, 0, NULL, 0, 17, 6)
INSERT [dbo].[friend] ([friendId], [block], [dateAccept], [status], [recevice_id], [sender_id]) VALUES (13, 0, NULL, 0, 18, 6)
SET IDENTITY_INSERT [dbo].[friend] OFF
/****** Object:  Table [dbo].[authentication]    Script Date: 10/11/2021 21:53:41 ******/
SET IDENTITY_INSERT [dbo].[authentication] ON
INSERT [dbo].[authentication] ([id], [enable], [role], [user_id]) VALUES (1, 1, N'ROLE_USER', 2)
INSERT [dbo].[authentication] ([id], [enable], [role], [user_id]) VALUES (2, 1, N'ROLE_ADMIN', 2)
INSERT [dbo].[authentication] ([id], [enable], [role], [user_id]) VALUES (3, 1, N'ROLE_USER', 3)
INSERT [dbo].[authentication] ([id], [enable], [role], [user_id]) VALUES (4, 1, N'ROLE_USER', 5)
INSERT [dbo].[authentication] ([id], [enable], [role], [user_id]) VALUES (5, 1, N'ROLE_USER', 6)
INSERT [dbo].[authentication] ([id], [enable], [role], [user_id]) VALUES (9, 1, N'ROLE_USER', 7)
INSERT [dbo].[authentication] ([id], [enable], [role], [user_id]) VALUES (10, 1, N'ROLE_USER', 9)
INSERT [dbo].[authentication] ([id], [enable], [role], [user_id]) VALUES (11, 1, N'ROLE_USER', 10)
INSERT [dbo].[authentication] ([id], [enable], [role], [user_id]) VALUES (12, 1, N'ROLE_USER', 12)
SET IDENTITY_INSERT [dbo].[authentication] OFF
/****** Object:  Table [dbo].[address]    Script Date: 10/11/2021 21:53:41 ******/
SET IDENTITY_INSERT [dbo].[address] ON
INSERT [dbo].[address] ([addressId], [country], [district], [numberaddress], [provinece], [ward], [user_id]) VALUES (1, N'x', N'x', N'x', N'x', N'x', 2)
INSERT [dbo].[address] ([addressId], [country], [district], [numberaddress], [provinece], [ward], [user_id]) VALUES (2, N'y', N'y', N'y', N'y', N'y', 2)
SET IDENTITY_INSERT [dbo].[address] OFF
/****** Object:  Table [dbo].[accuracy]    Script Date: 10/11/2021 21:53:41 ******/
