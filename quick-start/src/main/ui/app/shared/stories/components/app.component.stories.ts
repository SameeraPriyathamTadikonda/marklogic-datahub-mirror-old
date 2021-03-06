import { Component, NgModule, Directive, HostListener, Renderer2, ElementRef, Input  } from '@angular/core';
import {moduleMetadata, storiesOf} from '@storybook/angular';
import {object, text, boolean, withKnobs} from '@storybook/addon-knobs';
import {withNotes} from '@storybook/addon-notes';
import {action} from '@storybook/addon-actions';
import {linkTo} from '@storybook/addon-links';
import {Route, RouterModule} from "@angular/router";
import {RouterTestingModule} from "@angular/router/testing";
import {centered} from '@storybook/addon-centered/angular';
import {StoryCardComponent} from '../utils/story-card/story-card.component';
import {ThemeModule} from "../../components";
import {AppUiComponent} from "../../components";
import {HeaderComponent} from './../../../header/header.component';
import {HeaderUiComponent} from './../../../shared/components/header/header-ui.component';
import {HttpModule} from '@angular/http';
import { AuthService } from './../../../auth/auth.service';
import { ProjectService } from './../../../projects';
import { JobListenerService } from './../../../jobs/job-listener.service';
import { EnvironmentService } from './../../../environment';
import { STOMPService } from './../../../stomp';
import * as _ from 'lodash';


class MockAuthService { 
  setAuthenticated() {}
}
class MockProjectService { 
  logout() { }
}
class MockSTOMPService { }
class MockJobListenerService { 
  runningJobCount() {
    return 0;
  }
  totalPercentComplete() {}
}
class MockEnvironmentService { 
  marklogicVersion: '9.0-8'
}

let paths = [ '', 'entities', 'mappings', 'flows', 'jobs', 'traces', 'browse', 'settings' ];
let routeDef = [];
let classDefs = [];
_.forEach(paths, (path: string) => {
  @Component({
    template: `
    <div layout-padding layout="column" layout-align="center center">
      <h3>Current Path: ${path}</h3>
    </div>
    `
  })
  class DummyComponent { }
  classDefs.push(DummyComponent);
  
  routeDef.push({
    path, 
    component: DummyComponent
  });
})

const routes: Route[] = routeDef;

storiesOf('Components|App', module)
    .addDecorator(withKnobs)
    .addDecorator(centered)
    .addDecorator(
        moduleMetadata({
            imports: [
              HttpModule,
              ThemeModule,
              RouterModule,
              RouterTestingModule.withRoutes(routes)
            ],
            schemas: [],
            declarations: [
              AppUiComponent, 
              StoryCardComponent, 
              HeaderComponent, 
              HeaderUiComponent,
              ...classDefs
            ],
            providers: [
              { provide: AuthService, useValue: new MockAuthService() },
              { provide: ProjectService, useValue: new MockProjectService() },
              { provide: JobListenerService, useValue: new MockJobListenerService() },
              { provide: EnvironmentService, useValue: new MockEnvironmentService() },
              { provide: STOMPService, useValue: new MockSTOMPService() }
            ]
        })
    )
    .add('App Component', () => ({
        template: `
            <mlui-dhf-theme>
              <mlui-story-card [width]="'1300px'" [height]="'800px'">
                <app-ui
                  [canShowHeader]="canShowHeader"
                  [headerOffset]="headerOffset"
                ></app-ui>
              </mlui-story-card>
            </mlui-dhf-theme>
        `,
        props: {
          canShowHeader: boolean('canShowHeader', true),
          headerOffset: text('headerOffset', '64px'),
        },
    }));
