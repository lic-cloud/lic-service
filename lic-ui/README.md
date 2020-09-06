# lic-ui

## 技术栈
* Vue
* Axios
* Element UI

## 前端运行环境部署
```
1.进入前端目录：cd lic-ui 
2.yarn install 或 npm install
3.npm run serve 

补充：若运行后无法使用anios、element ui则在前端目录安装：
1.安装element-ui
1).cnpm install element-ui --save
2).项目src目录下的main.js，引入element-ui依赖
    import Element from 'element-ui'
    import "element-ui/lib/theme-chalk/index.css"
    Vue.use(Element)

2.安装axios
1).cnpm install axios --save
2).在main.js中全局引入axios
    import axios from 'axios'
    Vue.prototype.$axios = axios
```
