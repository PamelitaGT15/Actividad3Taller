git checkout develop
git pull
echo "# Test Travis" >> README.md
git add .
git commit -m "Prueba: activar Travis CI"
git push
