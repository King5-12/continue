#!/bin/bash

# Continue IntelliJ 插件完整构建脚本
# 确保UI是最新版本的正确构建流程

set -e  # 遇到错误立即退出

echo "🚀 开始构建 Continue IntelliJ 插件..."

# 步骤1: 构建GUI
echo "📦 步骤1: 构建GUI前端..."
cd gui
npm install
npm run build
cd ..

# 步骤2: 复制GUI构建产物到IntelliJ资源目录
echo "📋 步骤2: 复制GUI资源到IntelliJ插件..."
# 确保目标目录存在
mkdir -p extensions/intellij/src/main/resources/webview

# 复制构建后的文件
cp -r gui/dist/* extensions/intellij/src/main/resources/webview/

# 步骤3: 构建二进制文件
echo "🔧 步骤3: 构建二进制文件..."
cd binary
node build.js
cd ..

# 步骤4: 构建IntelliJ插件
echo "🏗️ 步骤4: 构建IntelliJ插件..."
cd extensions/intellij
./gradlew buildPlugin
cd ../..

echo "✅ 构建完成！插件文件位于: extensions/intellij/build/distributions/"
echo "📁 可以在以下IDE中安装该插件文件:"
echo "   - IntelliJ IDEA"
echo "   - JetBrains Rider"
echo "   - 其他基于IntelliJ平台的IDE"
