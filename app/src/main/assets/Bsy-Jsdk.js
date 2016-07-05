/**
 * 乙方, 回调JSON数据响应格式举例说明：
 * {
 *  "pcode" : "BSY-BIZ-10001", // 甲方分配乙方平台编码
 *  "token" : "jkyls86kh23dl2h32llj3hk23h", // 授权指令
 *  "stime" : "192382087827322", // 授权开始时间
 *  "etime" : "192382089212345", // 授权截止时间
 *  "status" : "1" // 0表示失败, 1表示成功
 *  }
 * 甲方, APP转换结果数据举例说明：
 * 数据格式不正确/正确且授权错误：[null][null][null][null][0]
 * 数据格式正确且授权正确：[BSY-10001][jkyls86kh23dl2h32llj3hk23h][19238208782732][19238208782732][1]
 */
BSY={Oauth:function(o){if(typeof o==="undefined"||o==null){BSYAPP.jsCallackFailure("[null][null][null][null][0]");return}var d=o.replace(/(^\s*)|(\s*$)/g,"");if(d===""){BSYAPP.jsCallackFailure("[null][null][null][null][0]");return}var s="",v=(new Function("","return "+d))();for(var i in v){s+="["+v[i]+"]"}BSYAPP.jsCallackSuccess(s);return}};