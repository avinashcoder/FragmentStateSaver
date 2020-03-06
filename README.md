[![](https://jitpack.io/v/avinashcoder/FragmentStateSaver.svg)](https://jitpack.io/#avinashcoder/FragmentStateSaver)

# Fragment State Saver

An Android library that holds fragment states. The fragment will be created only for first time after that the state will be save in the transaction. It is best for bottom navigation view where user navigate the pages multiple times with bottom navigation view. So that the fragment will not create again and again it will be visible from the back stack.

## Installation

Add Jitpack to project level gradle file     [![](https://jitpack.io/v/avinashcoder/FragmentStateSaver.svg)](https://jitpack.io/#avinashcoder/FragmentStateSaver)

```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```  

Then add library to module level gradle file
```
dependencies {
	implementation 'com.github.avinashcoder:FragmentStateSaver:1.0.0'
}
```
![ezgif com-resize](https://user-images.githubusercontent.com/34886720/76087019-54003e00-5fdb-11ea-9c03-77b1a49c393a.gif)

## Usage

Create a FragmentStateSaver instance in the onCreate function of your Activity

```

FragmentStateSaver fragmentStateSaver;

@Override
protected void onCreate(Bundle savedInstanceState) {
    //....Your Code...
    
    FrameLayout container = findViewById(R.id.container);

     fragmentStateSaver = new FragmentStateSaver(container, getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new SearchFragment();
                case 2:
                    return new ProfileFragment();
                default:
                    return new MessageFragment();
            }
        }
    };
}
```

To switch or change the current fragment call like this
```
fragmentStateSaver.changeFragment(0); // 0 is the index of the fragment, fragment will not create if its already created

```
If you want to reload or recreate the life cycle of fragment then you can do like this
```
fragmentStateSaver.removeFragment(position);
fragmentStateSaver.changeFragment(position); 
```

## Integration with BottomNavigationView

```

 bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
     @Override
     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         switch (item.getItemId()) {
             case R.id.home:
                 fragmentStateSaver.changeFragment(0);
                 return true;
             case R.id.search:
                 fragmentStateSaver.changeFragment(1);
                 return true;
             case R.id.profile:
                 fragmentStateSaver.changeFragment(2);
                 return true;
             default:
                 fragmentStateSaver.changeFragment(3);
                 return true;
         }
     }
 });
 
 ```
     
     
