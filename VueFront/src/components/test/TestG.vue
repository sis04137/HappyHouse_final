<template>
  <nav class="navigation">
    <ul class="menu">
      <li class="menu__item">
        <a href="#" class="menu__link">
          <span class="menu__title">
            <span class="menu__first-word" data-hover="About"> About </span>
            <span class="menu__second-word" data-hover="Us"> Us </span>
          </span>
        </a>
      </li>

      <li class="menu__item">
        <a href="#" class="menu__link">
          <span class="menu__title">
            <span class="menu__first-word" data-hover="Our"> Our </span>
            <span class="menu__second-word" data-hover="History">
              History
            </span>
          </span>
        </a>
      </li>

      <li class="menu__item">
        <a href="#" class="menu__link">
          <span class="menu__title">
            <span class="menu__first-word" data-hover="Latest"> Latest </span>
            <span class="menu__second-word" data-hover="News"> News </span>
          </span>
        </a>
      </li>

      <li class="menu__item">
        <a href="#" class="menu__link">
          <span class="menu__title">
            <span class="menu__first-word" data-hover="New"> New </span>
            <span class="menu__second-word" data-hover="Arrivals">
              Arrivals
            </span>
          </span>
        </a>
      </li>

      <li class="menu__item">
        <a href="#" class="menu__link">
          <span class="menu__title">
            <span class="menu__first-word" data-hover="On"> On </span>
            <span class="menu__second-word" data-hover="Sale"> Sale </span>
          </span>
        </a>
      </li>

      <li class="menu__item">
        <a href="#" class="menu__link">
          <span class="menu__title">
            <span class="menu__first-word" data-hover="Contact"> Contact </span>
            <span class="menu__second-word" data-hover="Us"> Us </span>
          </span>
        </a>
      </li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "TestG",
  data() {
    return {
      detailDrawer: null,
      detailMenu: [
        { icon: "mdi-currency-usd" },
        { icon: "mdi-account-voice" },
        { icon: "mdi-town-hall" },
        { icon: "mdi-account-group" },
      ],
    };
  },
  created() {},
  computed: {},
};
</script>

<style lang="scss">
// Colors
$color-primary: #0c8040;
$color-secondary: #626262;
$color-border: #ccd7d7;

// Media Queries breakpoints
$small: 480px;
$medium: 768px;
$large: 992px;
$x-large: 1200px;

@mixin hide-menu-items($index) {
  &:nth-child(n + #{$index + 1}) {
    display: none;
  }

  &:nth-child(#{$index}) .menu__link {
    border-right: 0;
  }
}

body {
  display: flex;
  align-items: center;
  height: 100vh;
  background: #fefefe;
}

a {
  outline: 0;
  text-decoration: none;
}

.navigation {
  width: 100%;
  border-bottom: 3px solid $color-border;
  // font-family: "Pathway Gothic One", sans-serif;
  font-size: 22px;
}

.menu {
  display: flex;
  justify-content: center;
  max-width: 1150px;
  margin: 0 auto;
  padding-left: 0;
}

.menu__item {
  display: inline-block;
  white-space: nowrap;

  @media screen and (max-width: $small) {
    @include hide-menu-items(2);
  }

  @media screen and (max-width: $medium) {
    @include hide-menu-items(3);
  }

  @media screen and (max-width: $large) {
    @include hide-menu-items(4);
  }

  &:last-child .menu__link {
    border-right: 0;
  }
}

.menu__link {
  display: block;
  padding: 6px 30px 3px;
  border-right: 2px dotted $color-border;

  &:hover,
  &:focus {
    .menu__first-word {
      transform: translate3d(0, -105%, 0);
    }

    .menu__second-word {
      transform: translate3d(0, 105%, 0);
    }
  }

  @media screen and (min-width: $medium) {
    padding: 6px 40px 3px;
  }
}

.menu__title {
  display: inline-block;
  overflow: hidden;
}

.menu__first-word,
.menu__second-word {
  display: inline-block;
  position: relative;
  transition: transform 0.3s;

  &::before {
    position: absolute;
    content: attr(data-hover);
  }
}

.menu__first-word {
  color: $color-primary;

  &::before {
    top: 105%;
    color: $color-secondary;
  }
}

.menu__second-word {
  color: $color-secondary;

  &::before {
    bottom: 105%;
    color: $color-primary;
  }
}
</style>
