Tweet:
item_tweet_summary （推文摘要，主页转发推文，或者新建转发推文用到）
item_tweet（用户发的推文，做个图标，表示是官方的）  搜索微博就是这3种内容
item_team（球队发的推文）
item_news（新闻发的推文）

User:
item_user（普通用户，现在用于搜索）
item_friend（我的好友，包括我关注的，粉丝，带有英文分类的）
item_discover（附近的人）

Message：
item_message（普通消息，包括官方的消息）
item_focus（关注，或者取消关注消息）
item_reply（包括微博评论，别人评论你）
item_star（别人赞你）
item_prompt（转发你的或者是@你的）
item_chat（私信）


activity:
SignActivity
NavActivity
SettingActivity
HelpActivity
WebActivity


fragment:
NavFragment





main:
MainFragment 
TweetFragment
HomeFragment
TeamFragment
NewsFragment
DiscoverFragment

contact:
ContactFragment
UserFragment
FriendFragment
FocusFragment
FansFragment

message:
MessageFragment


search:
SearchFragment
SearchTweetFragment(TweetFragment)
SearchUserFragment

sign:
LoginFragment
RegisterFragment
MeDetailFragment （UserDetailFragment）


add:
AddTweetFragment  （新建推文）
AddTeamFragment  （添加球队关注）


detail：
UserDetailFragment （用户详情）
TweetDetailFragment （推文详情）
NewsDetailFragment （新闻详情）




adapter:
BindingAdapter
TweetAdapter
UserAdapter
MessageAdapter
ChatAdapter





数据类型
Content:
id
title
summary
text
List<String> images
cover
url
lon
lat
extras

User:
id
username
name
signature
password
avatar
cover
gender (unknwon,male,female)
distance
time
lon
lat
type (normal,vip,super,team)
extras

Tweet:
id
uid
icon
name
source
Content content
time
starCount
repeatCount
commentCount
mainType (user,team,news)
detailType (tweet,news)
extras

Message
id
uid
icon
name
Content
time
type (focus,star,prompt,reply,chat,message)
extras