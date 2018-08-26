![LUX Logo](ui/src/main/resources/io/luxcore/fx/lux_logo_horizontal.png)

"FIRST OF ITS KIND"

Luxcore is GNU AGPLv3 licensed.

[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2F216k155%2Flux.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2F216k155%2Flux?ref=badge_shield) [![Build Status](https://travis-ci.org/LUX-Core/lux.svg?branch=master)](https://travis-ci.org/LUX-Core/lux) [![GitHub version](https://badge.fury.io/gh/LUX-Core%2Flux.png)](https://badge.fury.io/gh/LUX-Core%2Flux.png) [![HitCount](http://hits.dwyl.io/216k155/lux.svg)](http://hits.dwyl.io/216k155/lux)
<a href="https://discord.gg/27xFP5Y"><img src="https://discordapp.com/api/guilds/364500397999652866/embed.png" alt="Discord server" /></a> <a href="https://twitter.com/intent/follow?screen_name=LUX_COIN"><img src="https://img.shields.io/twitter/follow/LUX_COIN.svg?style=social&logo=twitter" alt="follow on Twitter"></a>

[Website](https://luxcore.io) — [LUXtre + LUXGate](https://github.com/LUX-Core/luxtre) - [PoS Web Wallet](https://lux.poswallet.io) — [Block Explorer](https://explorer.luxcore.io/) — [Blog](https://reddit.com/r/LUXCoin) — [Forum](https://bitcointalk.org/index.php?topic=2254046.0) — [Telegram](https://t.me/LUXcoinOfficialChat) — [Twitter](https://twitter.com/LUX_Coin)

### Luxgate Desktop Client
----------------------

Ensure you have installed Oracle JDK8 on your dev machine.
Project should work using OpenJDK distribution too.


    sudo add-apt-repository ppa:webupd8team/java
    sudo apt-get update
    sudo apt-get install oracle-java8-installer

Run app in development mode


    ./gradlew ui:launch


Make `.deb`

    ./gradlew ui:deb
    sudo dpkg -i ui/build/jfx/native/luxgatej-1.0.deb



### Testing
-------

Testing and code review is the bottleneck for development; we get more pull
requests than we can review and test on short notice. Please be patient and help out by testing
other people's pull requests, and remember this is a security-critical project where any mistake might cost people
lots of money.

### Manual Quality Assurance (QA) Testing
-----------------------------------------
Changes should be tested by somebody other than the developer who wrote the
code. This is especially important for large or high-risk changes. It is useful
to add a test plan to the pull request description if testing the changes is
not straightforward.

### Issue
---------
 We try to prompt our users for the basic information We always need for new issues.
 Please fill out the issue template below and submit it to our discord channel
 
  <a href="https://discord.gg/27xFP5Y"><img src="https://discordapp.com/api/guilds/364500397999652866/embed.png" alt="Discord server" /></a>

[ISSUE_TEMPLATE](https://github.com/LUX-Core/lux/blob/master/doc/template/ISSUE_TEMPLATE_example.md)
 
## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2F216k155%2Flux.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2F216k155%2Flux?ref=badge_large)
