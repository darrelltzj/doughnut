import { routerScopeGuard } from '@/routes/relative_routes'

describe('router guards', () => {
  var confirm;
  const guard = routerScopeGuard("repeat", ["review"], async ()=>confirm())

  beforeEach(async () => {
    confirm = jest.fn()
  });
  test('when in repeat, go to nested noteShow', async () => {
    const next = jest.fn()
    await guard({name: 'noteShow', params: {noteid: 3}}, {name: 'repeat'}, next)
    expect(next).toHaveBeenCalledWith({name: 'repeat-noteShow', params: {noteid: 3}});
  })

  test('when in repeat, and going to already nested route', async () => {
    const next = jest.fn()
    await guard({name: 'repeat-noteShow', params: {noteid: 3}}, {name: 'repeat'}, next)
    expect(next).toHaveBeenCalledWith();
  })

  test('when in repeat, and going to a route that doesnot have nested route', async () => {
    const next = jest.fn()
    confirm.mockReturnValue(true);
    await guard({name: 'initial', params: {noteid: 3}}, {name: 'repeat'}, next)
    expect(next).toHaveBeenCalledWith();
  })

  test('when in repeat, and going to a route that doesnot have nested route and not confirm', async () => {
    const next = jest.fn()
    confirm.mockReturnValue(false);
    await guard({name: 'initial'}, {name: 'repeat'}, next)
    expect(next).toHaveBeenCalledTimes(0);
  })

  test('when allowed route is called', async () => {
    const next = jest.fn()
    confirm.mockReturnValue(false);
    await guard({name: 'review'}, {name: 'repeat'}, next)
    expect(confirm).toHaveBeenCalledTimes(0);
  })

})
